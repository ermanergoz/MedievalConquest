package main;

import main.building.*;
import main.unit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePanel extends JPanel {
    private Image background_;
    private final int screenHeight_;
    private final int screenWidth_;
    private final Player player_;

    private final ActionListener repaint_;
    private final ActionListener showDialog_;
    private final ActionListener removeOpponentBuildable_;
    private final ActionListener endTurn_;

    private String dialogMessage_ = "";
    private String dialogTitle_ = "";

    private ArrayList<JButton> buttons_;
    private JButton endTurnButton_;
    private ArrayList<JLabel> statNames_;
    private ArrayList<JLabel> statValues_;

    //Player-Based Labels
    private ArrayList<JLabel> inventory_;
    private ArrayList<JLabel> resValues_;

    private JLabel turnLabel_;
    private JLabel buildable_;

    private Sprite selectedSprite_ = null;
    private int turn_ = 1;


    public enum action {MOVE, ATTACK, CUTTREE}

    public action secondarySelectionAction_ = null;
    private Sprite secondarySelection_ = null;

    public Buildable opponentBuildableToBeRemoved_ = null;
    public boolean isDisabled = false;

    public boolean useAlternativeSprites = false;
    private Resources periodicResourceIncrease_ = new Resources(50, 50, 50);

    private int turnCounter_ = 0;


    public GamePanel(int screenWidth, int screenHeight, Player player,
                     ActionListener repaint, ActionListener showDialog,
                     ActionListener removeOpponentBuildable, ActionListener endTurn) {
        screenWidth_ = screenWidth;
        screenHeight_ = screenHeight;
        background_ = (new ImageIcon(new File("res/images/gamePanel.png").getAbsolutePath())).getImage();
        player_ = player;

        repaint_ = repaint;
        showDialog_ = showDialog;
        removeOpponentBuildable_ = removeOpponentBuildable;
        endTurn_ = endTurn;


        this.setLayout(null);
        endTurnButton_ = new JButton();
        endTurnButton_.setBounds(428, 16, 97, 35);
        buttonStyle(endTurnButton_, 15);
        endTurnButton_.addActionListener(e -> endTurn());

        buttons_ = new ArrayList<>();
        buttons_.addAll(Arrays.asList(new JButton("-"),
                new JButton("-"),
                new JButton("-"),
                new JButton("-"),
                new JButton("-"),
                new JButton("-")
        ));

        placeButtons();

        resValues_ = new ArrayList<>();
        Resources resources = player.getInventory_().getResources_();
        resValues_.addAll(Arrays.asList(new JLabel(String.valueOf(resources.getnWood_())),
                new JLabel(String.valueOf(resources.getnGold_())),
                new JLabel(String.valueOf(resources.getnIron_()))
        ));

        placeLabels(resValues_, 475, 81, 40);

        statNames_ = new ArrayList<>();
        statNames_.addAll(Arrays.asList(new JLabel("HP:"),
                new JLabel("Range:"),
                new JLabel("DMG:")
        ));

        placeLabels(statNames_, 190, 70, 40);

        statValues_ = new ArrayList<>();
        statValues_.addAll(Arrays.asList(new JLabel(),
                new JLabel(),
                new JLabel()
        ));

        placeLabels(statValues_, 190, 90, 40);

        turnLabel_ = new JLabel("Turn: " + turn_);
        turnLabel_.setBounds(190, 37, 40, 20);
        this.add(turnLabel_);

        inventory_ = new ArrayList<>();

        inventory_.addAll(Arrays.asList(new JLabel(),
                new JLabel(),
                new JLabel(),
                new JLabel(),
                new JLabel(),
                new JLabel()
        ));


        placeItems();
        addItemLabelMouseListener();
        buildable_ = new JLabel(new ImageIcon());
        buildable_.setBounds(34, 29, 145, 165);
        this.add(buildable_);
    }

    private void placeItems() {
        int xCoord = 285;
        int yCoord = 19;

        for (int i = 0; i < inventory_.size(); ++i) {
            this.add(inventory_.get(i));
            inventory_.get(i).setBounds(xCoord, yCoord, 52, 53);
            xCoord = xCoord + 59;

            if (i % 2 != 0) {
                xCoord = 285;
                yCoord = yCoord + 62;
            }
        }
    }

    private void placeLabels(ArrayList<JLabel> labels, int xCoord, int yCoord, int space) {
        for (JLabel label : labels) {
            label.setBounds(xCoord, yCoord, 40, 20);
            yCoord = yCoord + space;
            this.add(label);
        }
    }

    private void placeButtons() {
        int buttonWidth = 207;
        int buttonHeight = 88;
        int xCoord = 551;
        int yCoord = 15;

        for (int i = 0; i < buttons_.size(); ++i) {
            buttonStyle(buttons_.get(i), 28);
            buttons_.get(i).setBounds(xCoord, yCoord, buttonWidth, buttonHeight
            );
            xCoord = xCoord + buttonWidth;

            if (i == 2) {
                xCoord = 551;
                yCoord = yCoord + buttonHeight;
            }
        }
    }

    private void buttonStyle(JButton button, int textSize) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Ariel", Font.ITALIC, textSize));
        this.add(button);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(background_, 0, 0, screenWidth_, screenHeight_, null);
//        updateSelectedSpriteData();
        updateResources();
        turnLabel_.setText("Turn: " + turn_);
    }

    public void updateSelectedSpriteData() {
        if (selectedSprite_ == null) {
            resetSelectedSpriteOptions();
            return;
        }

        if (selectedSprite_ instanceof NeutralBazaar) {
            NeutralBazaar nb = (NeutralBazaar) selectedSprite_;

            updateButtons(new ButtonInfo[]{new ButtonInfo("Buy", e1 -> updateButtons(new ButtonInfo[]{
                    new ButtonInfo("Health Potion", new BuyItemListener(new Item("Health Potion", new Resources(10, 50, 30), "potion.png", player_))),
                    new ButtonInfo("Iron Sword", new BuyItemListener(new Item("Iron Sword", new Resources(150, 50, 40), "sword.png", player_))),
                    new ButtonInfo("<html><center>Sword of " + "the Gods</center></html>", new BuyItemListener(new Item("Sword of the Gods", new Resources(300, 100, 100), "swordOfGods.png", player_))),
                    new ButtonInfo("Shield", new BuyItemListener(new Item("Shield", new Resources(74, 45, 5), "shield.png", player_))),
                    new ButtonInfo("Compass", new BuyItemListener(new Item("Compass", new Resources(20, 0, 100), "compass.png", player_))),
                    new ButtonInfo("<html><center>Arms of </center>" + "Morpheus</center></html>", new BuyItemListener(new Item("Arms of Morpheus", new Resources(320, 150, 120), "morpheus.png", player_)))
            })), new ButtonInfo("Sell", e1 -> updateButtons(new ButtonInfo[]{
                    new ButtonInfo("Health Potion", new SellItemListener("Health Potion")),
                    new ButtonInfo("Iron Sword", new SellItemListener("Iron Sword")),
                    new ButtonInfo("<html><center>Sword of " + "the Gods</center></html>", new SellItemListener("Sword of the Gods")),
                    new ButtonInfo("Shield", new SellItemListener("Shield")),
                    new ButtonInfo("Compass", new SellItemListener("Compass")),
                    new ButtonInfo("<html><center>Arms of </center>" + "Morpheus</center></html>", new SellItemListener("Arms of Morpheus"))
            }))});
            setStatValues(true);  //won't show HP, Range and DMG
            setBuildableLabelImage(nb);

        } else { // it's a Tile
            Tile tile = (Tile) selectedSprite_;
            if (tile.getBuildable_() == null) {
                resetSelectedSpriteOptions();
                setStatValues(/*isBazaar*/false);  //won't show HP, Range and DMG
                setBuildableLabelImage(null);
                if (tile.getType_() == Tile.TileType.PLAIN && ((player_.getHeadquarters().getX_() == 0 && tile.getX_() / 40 < 15) || (player_.getHeadquarters().getX_() != 0 && tile.getX_() / 40 > 15))) {
                    updateButtons(new ButtonInfo[]{new ButtonInfo("Create", e -> {
                        int x = selectedSprite_.getX_();
                        int y = selectedSprite_.getY_();
                        int width = selectedSprite_.getWidth_();
                        int height = selectedSprite_.getHeight_();

                        updateButtons(new ButtonInfo[]{
                                new ButtonInfo("Unit", e1 -> updateButtons(new ButtonInfo[]{
                                        new ButtonInfo("Soldier", new CreateBuildableListener(new Soldier(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("Archer", new CreateBuildableListener(new Archer(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("TreeCutter", new CreateBuildableListener(new TreeCutter(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("Cavalry", new CreateBuildableListener(new Cavalry(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("Catapult", new CreateBuildableListener(new Catapult(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("Amphibious", new CreateBuildableListener(new Amphibious(x, y, width, height, useAlternativeSprites)))
                                })),
                                new ButtonInfo("Building", e2 -> updateButtons(new ButtonInfo[]{
                                        new ButtonInfo("Barracks", new CreateBuildableListener(new Barracks(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("Machinery", new CreateBuildableListener(new Machinery(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("Mine", new CreateBuildableListener(new Mine(x, y, width, height, useAlternativeSprites))),
                                        new ButtonInfo("Wall", new CreateBuildableListener(new Wall(x, y, width, height, useAlternativeSprites)))
                                }))
                        });
                    })});
                } else {
                    buttons_.get(0).setText("Cannot build");
                    buttons_.get(1).setText("too far");
                    buttons_.get(2).setText("from the");
                    buttons_.get(3).setText("headquarters");
                }
                return;
            }

            boolean isUnit = true;
            try {
                Unit u = (Unit) tile.getBuildable_();
            } catch (Exception exception) {
                isUnit = false;
            }

            // Only showing stats, if it's opponent's buildable
            if (!player_.isOwnerOf(tile.getBuildable_())) {
                resetSelectedSpriteOptions();
                setBuildableLabelImage(tile.getBuildable_());
                if (isUnit) {
                    updateUnitStatValues((Unit) tile.getBuildable_());
                } else {
                    updateBuildingStatValues(tile.getBuildable_());
                }
                return;
            }

            ButtonInfo removeBI = new ButtonInfo("Remove", rm -> {
                if (!(selectedSprite_ instanceof Tile)) {
                    return;
                }
                Tile t = (Tile) selectedSprite_;
                if (t.getBuildable_() == null || !player_.isOwnerOf(t.getBuildable_())) {
                    return;
                }
                Buildable b = t.getBuildable_();

                t.setBuildable_(null); // removing from map
                player_.removeBuildable(b); // removing from player

                // Giving back half of the buildable's price to the player.
                Inventory inventory = player_.getInventory_();
                Resources halfCost = new Resources(b.getCost().getnIron_() / 2, b.getCost().getnWood_() / 2, b.getCost().getnGold_() / 2);
                inventory.setResources_(new Resources(inventory.getResources_().getnIron_() + halfCost.getnIron_(),
                        inventory.getResources_().getnWood_() + halfCost.getnWood_(),
                        inventory.getResources_().getnGold_() + halfCost.getnGold_()));

                resetSelectedSpriteOptions();
                repaint_.actionPerformed(null);
            });

            if (isUnit) {

                ButtonInfo attackBI = new ButtonInfo("Attack", atk -> this.secondarySelectionAction_ = action.ATTACK);
                ButtonInfo moveBI = new ButtonInfo("Move", mv -> this.secondarySelectionAction_ = action.MOVE);


                if (tile.getBuildable_() instanceof TreeCutter) {
                    TreeCutter treeCutter = (TreeCutter) tile.getBuildable_();
                    updateUnitStatValues(treeCutter);
                    setBuildableLabelImage(treeCutter);
                    updateButtons(new ButtonInfo[]{moveBI, attackBI, new ButtonInfo("Cut Tree", cTree -> this.secondarySelectionAction_ = action.CUTTREE), removeBI});
                } else { // Is any other Unit except TreeCutter
                    Unit unit = (Unit) tile.getBuildable_();
                    updateUnitStatValues(unit);
                    setBuildableLabelImage(unit);
                    updateButtons(new ButtonInfo[]{moveBI, attackBI, removeBI});

                }
            } else { // It's a building
                Buildable buildable = tile.getBuildable_();
                updateBuildingStatValues(buildable);
                setBuildableLabelImage(buildable);

                // Special case: Headquarters doesn't have the remove option
                if (tile.getBuildable_() instanceof Headquarters) {
                    updateButtons(new ButtonInfo[]{});
                } else {
                    updateButtons(new ButtonInfo[]{removeBI});
                }
            }
        }
    }

    private void resetSelectedSpriteOptions() {
        updateButtons(new ButtonInfo[]{});
        setBuildableLabelImage(null);
        for (int i = 0; i < statValues_.size(); ++i) {
            statValues_.get(i).setVisible(false);
            statNames_.get(i).setVisible(false);
        }
    }

    private void setBuildableLabelImage(Sprite sprite) {
        if (sprite == null || sprite.getImages_() == null)
            buildable_.setIcon(null);
        else
            buildable_.setIcon(new ImageIcon(sprite.getImages_()[1]));
    }

    //
    private void updateResources() {
        if (player_ == null) {
            return;
        }
        Resources r = player_.getInventory_().getResources_();
        resValues_.get(0).setText(String.valueOf(r.getnWood_())); //wood
        resValues_.get(1).setText(String.valueOf(r.getnGold_())); //gold
        resValues_.get(2).setText(String.valueOf(r.getnIron_())); //iron
    }

    private void setStatValues(boolean isBazaar) {
        if (isBazaar) {
            statValues_.get(0).setText(new String(Character.toString('\u221E').getBytes()));    //for infinity symbol
            statValues_.get(0).setVisible(true);
            statNames_.get(0).setVisible(true);

            for (int i = 1; i < statValues_.size(); ++i) {
                statValues_.get(i).setVisible(false);
                statNames_.get(i).setVisible(false);
            }
        } else {
            for (int i = 0; i < statValues_.size(); ++i) {
                statValues_.get(i).setVisible(false);
                statNames_.get(i).setVisible(false);
            }
        }
    }

    private void updateUnitStatValues(Unit unit) {
        statValues_.get(0).setText(String.valueOf(unit.getHp_()));   //HP
        statValues_.get(1).setText(String.valueOf(unit.getRange()));   //RANGE
        statValues_.get(2).setText(String.valueOf(unit.getDmg_()));//DMG

        for (int i = 0; i < statValues_.size(); ++i) {
            statValues_.get(i).setVisible(true);
            statNames_.get(i).setVisible(true);
        }
    }

    private void updateBuildingStatValues(Buildable buildable) {
        for (int i = 0; i < statValues_.size(); ++i) {
            if (i == 0) {
                statValues_.get(0).setText(String.valueOf(buildable.getHp_()));   //HP
                statValues_.get(0).setVisible(true);
                statNames_.get(0).setVisible(true);
            } else {
                statValues_.get(i).setVisible(false);
                statNames_.get(i).setVisible(false);
            }
        }
    }

    private void updateButtons(ButtonInfo[] buttonInfo) {
        // Inform that no action is possible when it's not your turn
        if (this.isDisabled) {
            buttons_.get(0).setText("Waiting for");
            buttons_.get(1).setText("opponent's");
            buttons_.get(2).setText("turn...");
            for (JButton b : buttons_) {
                b.setEnabled(false);
            }
            return;
        }

        if (buttonInfo.length > buttons_.size()) {
            return;
        }

        for (int i = 0; i < buttonInfo.length; i++) {
            buttons_.get(i).setText(buttonInfo[i].getText());
            for (ActionListener al : buttons_.get(i).getActionListeners()) {
                buttons_.get(i).removeActionListener(al);
            }
            buttons_.get(i).addActionListener(buttonInfo[i].getActionListener());
            buttons_.get(i).setEnabled(true);
        }
        for (int i = 0; i < (buttons_.size() - buttonInfo.length); i++) {
            JButton button = buttons_.get(buttonInfo.length + i);
            button.setText("-");
            button.setEnabled(false);
            for (ActionListener al : button.getActionListeners()) {
                button.removeActionListener(al);
            }
        }
    }

    // TODO: Needs testing
    public void handleSecondarySelection() {
        if (!(secondarySelection_ instanceof Tile)) {
            return;
        } // Ignore click if it's not a Tile
        Tile tile = (Tile) secondarySelection_;
        Unit unit = (Unit) ((Tile) selectedSprite_).getBuildable_();

        action secAction = secondarySelectionAction_;
        cancelSecondarySelection();

        switch (secAction) {
            case MOVE:
                if (!unit.move(tile)) {
                    showDialogWithError("Moving not Possible", "Tile is out of range, is invalid, or this unit has exceeded the number of actions for the turn.");
                    return;
                }

                Tile t = (Tile) selectedSprite_;
                t.setBuildable_(null);
                break;
            case ATTACK:
                // If tile doesn't have a buildable or it's from the same player -> not valid
                if (tile.getBuildable_() == null || player_.isOwnerOf(tile.getBuildable_())) {
                    showDialogWithError("Target Not Valid", "Selected target is not valid.");
                    return;
                }

                if (!unit.attack(tile.getBuildable_())) {
                    showDialogWithError("Out of Range", "Enemy buildable is out of range or this unit has exceeded the number of actions for the turn.");
                    return;
                }

                if (tile.getBuildable_().getHp_() <= 0) {
                    opponentBuildableToBeRemoved_ = tile.getBuildable_();
                    removeOpponentBuildable_.actionPerformed(null);
                    tile.setBuildable_(null);
                }

                break;

            case CUTTREE:
                TreeCutter treeCutter = (TreeCutter) unit;
                if (!treeCutter.cutTree(tile)) {
                    showDialogWithError("Cutting Not Possible", "It's not possible to cut the selected target or this unit has exceeded the number of actions for the turn.");
                    return;
                }
                break;
        }

        repaint_.actionPerformed(null);
    }

    private void showDialogWithError(String title, String message) {
        dialogTitle_ = title;
        dialogMessage_ = message;
        showDialog_.actionPerformed(null);
    }

    private void cancelSecondarySelection() {
        secondarySelection_ = null;
        secondarySelectionAction_ = null;
        resetSelectedSpriteOptions();
    }

    public void setTurn(int turn) {
        this.turn_ = turn;
    }

    public Sprite getSelectedSprite_() {
        return selectedSprite_;
    }

    public void setSelectedSprite_(Sprite selectedSprite_) {
        this.selectedSprite_ = selectedSprite_;
    }

    public void setSecondarySelection_(Sprite secondarySelection_) {
        this.secondarySelection_ = secondarySelection_;
    }

    public String getDialogMessage() {
        return this.dialogMessage_;
    }

    public String getDialogTitle() {
        return this.dialogTitle_;
    }

    public void startTurn() {
        // Default increase in resources per turn
        player_.getInventory_().setResources_(new Resources(
                player_.getInventory_().getResources_().getnIron_() + periodicResourceIncrease_.getnIron_(),
                player_.getInventory_().getResources_().getnWood_() + periodicResourceIncrease_.getnWood_(),
                player_.getInventory_().getResources_().getnGold_() + periodicResourceIncrease_.getnGold_()
        ));


        this.isDisabled = false;
        this.endTurnButton_.setEnabled(true);
        this.resetSelectedSpriteOptions();
    }

    public void disableNewInteractions() {
        this.isDisabled = true;
        this.endTurnButton_.setEnabled(false);
        this.resetSelectedSpriteOptions();
    }

    private void endTurn() {
        disableNewInteractions();
        ++turnCounter_;

        //get extra resources every turn
        for (int i = 0; i < player_.getBuildables_().size(); ++i) {
            if (player_.getBuildables_().get(i) instanceof Mine) {
                player_.getInventory_().setResources_(new Resources(
                        player_.getInventory_().getResources_().getnIron_() + 10,
                        player_.getInventory_().getResources_().getnWood_() + 10,
                        player_.getInventory_().getResources_().getnGold_() + 10
                ));
            }

            //get resources for 1 soldier in every 5 turns
            if (player_.getBuildables_().get(i) instanceof Barracks && turnCounter_ == 5) {
                player_.getInventory_().setResources_(new Resources(
                        player_.getInventory_().getResources_().getnIron_() + 60,
                        player_.getInventory_().getResources_().getnWood_() + 0,
                        player_.getInventory_().getResources_().getnGold_() + 20
                ));
            }

            //get resources for 1 catapult in every 5 turns
            if (player_.getBuildables_().get(i) instanceof Machinery && turnCounter_ == 5) {
                player_.getInventory_().setResources_(new Resources(
                        player_.getInventory_().getResources_().getnIron_() + 160,
                        player_.getInventory_().getResources_().getnWood_() + 0,
                        player_.getInventory_().getResources_().getnGold_() + 135
                ));
            }
        }

        if (turnCounter_ >= 5)
            turnCounter_ = 0;

        // Reset number of moves for every unit of the player at the end of the turn
        for (Buildable b : player_.getBuildables_()) {
            try {
                ((Unit) b).resetNMoves();
            } catch (Exception ignored) {
            }
        }
        endTurn_.actionPerformed(null);
    }

    class CreateBuildableListener implements ActionListener {
        private final Buildable buildable_;

        public CreateBuildableListener(Buildable b) {
            buildable_ = b;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (buildable_ == null || player_ == null) {
                return;
            }
            // Buildable or player is not valid
            if (
                    player_.getInventory_().getResources_().getnGold_() <= buildable_.getCost().getnGold_() ||
                            player_.getInventory_().getResources_().getnIron_() <= buildable_.getCost().getnIron_() ||
                            player_.getInventory_().getResources_().getnWood_() <= buildable_.getCost().getnWood_()
            ) {

                showDialogWithError("Not Enough Resources", "Player still needs " + buildable_.getCost().minus(player_.getInventory_().getResources_()));
                return;
            }
            // Player does not have the resources to build

            player_.getInventory_().setResources_(player_.getInventory_().getResources_().minus(buildable_.getCost())); // subtracting resources

            player_.addBuildable(buildable_);
            Tile tile = (Tile) selectedSprite_;
            tile.setBuildable_(buildable_);
            resetSelectedSpriteOptions();
            repaint_.actionPerformed(e);
        }
    }

    class BuyItemListener implements ActionListener {
        private final Item item_;
        boolean doesExist_;

        public BuyItemListener(Item i) {
            item_ = i;
            doesExist_ = false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (item_ == null || player_ == null) {
                return;
            }
            if (player_.getInventory_().getResources_().isLessOrEqualTo(item_.getCost())) {
                showDialogWithError("Not Enough Resources", "Player still needs " + item_.getCost().minus(player_.getInventory_().getResources_()));
                return;
            }
            for (int i = 0; i < player_.getInventory_().getItems_().size(); ++i) {
                if (player_.getInventory_().getItems_().get(i).getDescription().equals(item_.getDescription())) {
                    doesExist_ = true;
                    break;
                }
            }
            if (!doesExist_) {
                player_.getInventory_().setResources_(player_.getInventory_().getResources_().minus(item_.getCost())); // subtracting resources
                player_.getInventory_().addItem(item_);
                updateItemLabelImages();
            }
        }
    }

    class SellItemListener implements ActionListener {
        private final String description_;
        boolean doesExist_;

        public SellItemListener(String str) {
            description_ = str;
            doesExist_ = false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (description_ == null || player_ == null) {
                return;
            }

            for (int i = 0; i < player_.getInventory_().getItems_().size(); ++i) {
                if (player_.getInventory_().getItems_().get(i).getDescription().equals(description_)) {
                    // player_.getInventory_().setResources_(player_.getInventory_().getResources_().minus(item_.getCost())); // subtracting resources
                    Resources halfCost = new Resources(player_.getInventory_().getItems_().get(i).getCost().getnIron_() / 2,
                            player_.getInventory_().getItems_().get(i).getCost().getnWood_() / 2,
                            player_.getInventory_().getItems_().get(i).getCost().getnGold_() / 2);

                    player_.getInventory_().setResources_(new Resources(player_.getInventory_().getResources_().getnIron_() + halfCost.getnIron_(),
                            player_.getInventory_().getResources_().getnWood_() + halfCost.getnWood_(),
                            player_.getInventory_().getResources_().getnGold_() + halfCost.getnGold_()));
                    player_.getInventory_().getItems_().get(i).setImage(null);
                    player_.getInventory_().removeItem(player_.getInventory_().getItems_().get(i));

                    updateItemLabelImages();
                    doesExist_ = true;
                    break;
                }
            }
            if (!doesExist_)
                showDialogWithError("Sale is not successful", "You don't own " + description_ + " item!");
        }
    }

    private void updateItemLabelImages() {
        for (int i = 0; i < inventory_.size(); ++i) {
            inventory_.get(i).setIcon(null);
        }
        for (int i = 0; i < player_.getInventory_().getItems_().size(); ++i) {
            inventory_.get(i).setIcon(player_.getInventory_().getItems_().get(i).getImage());
        }
    }

    class applyItemListener implements ActionListener {
        private final String description_;
        boolean doesExist_;

        public applyItemListener(String str) {
            description_ = str;
            doesExist_ = false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (description_ == null || player_ == null) {
                return;
            }

            for (int i = 0; i < player_.getInventory_().getItems_().size(); ++i) {
                if (player_.getInventory_().getItems_().get(i).getDescription().equals(description_)) {

                    player_.getInventory_().getItems_().get(i).setImage(null);
                    player_.getInventory_().getItems_().get(i).applyPerks(description_);
                    player_.getInventory_().removeItem(player_.getInventory_().getItems_().get(i));

                    updateItemLabelImages();
                    doesExist_ = true;
                    break;
                }
            }
            if (!doesExist_)
                showDialogWithError("You can't use this item", "You don't own " + description_);
        }
    }

    private void addItemLabelMouseListener() {
        for (int i = 0; i < inventory_.size(); ++i) {
            inventory_.get(i).addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    updateButtons(new ButtonInfo[]{
                            new ButtonInfo("Health Potion", new applyItemListener("Health Potion")),
                            new ButtonInfo("Iron Sword", new applyItemListener("Iron Sword")),
                            new ButtonInfo("<html><center>Sword of " + "the Gods</center></html>", new applyItemListener("Sword of the Gods")),
                            new ButtonInfo("Shield", new applyItemListener("Shield")),
                            new ButtonInfo("Compass", new applyItemListener("Compass")),
                            new ButtonInfo("<html><center>Arms of </center>" + "Morpheus</center></html>", new applyItemListener("Arms of Morpheus"))
                    });
                }
            });
        }
    }
}