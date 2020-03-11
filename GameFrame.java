package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame {
    private Player player_;

    private MapPanel northPanel_;
    private GamePanel southPanel_;

    private final int screenHeight_;
    private final int screenWidth_;
    private final int gamePanelHeight_;

    public Buildable opponentBuildableToBeRemoved = null;
    private ActionListener removeOpponentBuildable_;
    private ActionListener endTurn_;

    public GameFrame(String title, int screenWidth, int screenHeight, int gamePanelHeight,
                     Map map, Player player, ActionListener removeOpponentBuildable, ActionListener endTurn) {
        super(title);
        this.screenWidth_ = screenWidth;
        this.screenHeight_ = screenHeight;
        this.northPanel_ = new MapPanel(map);
        this.gamePanelHeight_ = gamePanelHeight;
        this.player_ = player;
        this.removeOpponentBuildable_ = removeOpponentBuildable;
        this.endTurn_ = endTurn;

        newGame();

        this.pack();
        this.setVisible(true);
    }


    private void newGame() {

        northPanel_.addMouseListener(new MouseAdapter() {
            /*
                Method that decides which kind of clickable sprite was selected
                and populates the southPanel_ (GamePanel) accordingly
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (southPanel_.secondarySelectionAction_ == null){
                    southPanel_.setSelectedSprite_(northPanel_.getMap().getSpriteAt(e.getX(),e.getY()));
                    southPanel_.updateSelectedSpriteData();
                    return;
                }
                // Secondary click (move, attack and cutTree methods)
                southPanel_.setSecondarySelection_(northPanel_.getMap().getSpriteAt(e.getX(), e.getY()));
                southPanel_.handleSecondarySelection();
            }
        });
        northPanel_.setPreferredSize(new Dimension(screenWidth_, screenHeight_ - gamePanelHeight_));

        this.getContentPane().add(northPanel_, BorderLayout.NORTH);

        southPanel_ = new GamePanel(screenWidth_, gamePanelHeight_, player_,
                repaint -> repaintUI(),
                showDialog -> JOptionPane.showMessageDialog(this, southPanel_.getDialogMessage(), southPanel_.getDialogTitle(), JOptionPane.ERROR_MESSAGE, null),
                removeBuildable -> removeOpponentBuildable(),
                endTurn -> endTurn_.actionPerformed(null)
        );
        southPanel_.setPreferredSize(new Dimension(screenWidth_, gamePanelHeight_));

        this.getContentPane().add(southPanel_, BorderLayout.SOUTH);
    }

    private void removeOpponentBuildable(){
        if (southPanel_.opponentBuildableToBeRemoved_ != null){
            this.opponentBuildableToBeRemoved = southPanel_.opponentBuildableToBeRemoved_;
            southPanel_.opponentBuildableToBeRemoved_ = null;
            removeOpponentBuildable_.actionPerformed(null);
        }
    }

    public void repaintUI(){
        southPanel_.setSelectedSprite_(null);
        southPanel_.repaint();
        northPanel_.repaint();
    }

    public Player getPlayer(){
        return player_;
    }

    public GamePanel getSouthPanel_() {
        return southPanel_;
    }
}
