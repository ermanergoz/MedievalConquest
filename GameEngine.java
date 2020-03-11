package main;


import main.building.Headquarters;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GameEngine {

    private final int screenHeight_;
    private final int screenWidth_;
    private Map map_;

    GameFrame frame1_;
    GameFrame frame2_;

    private String mapFilePath_ = "res/maps/map1.map";
    private final int gamePanelHeight_ = 200;

    boolean isPlayer1 = true;
    private int turn_ = 1;

    public GameEngine(int screenWidth, int screenHeight){

        this.screenWidth_ = screenWidth;
        this.screenHeight_ = screenHeight;

        map_ = new Map(new File(mapFilePath_).getAbsolutePath(), screenWidth_, screenHeight_ - gamePanelHeight_);

        Headquarters hq1 = new Headquarters(0, 0,40,40, false);
        Headquarters hq2 = new Headquarters(29 * 40,14 * 40,40,40, true);

        try{
            Tile t1 = (Tile) map_.getSpriteAt(hq1.getX_(), hq1.getY_());
            t1.setBuildable_(hq1);

            Tile t2 = (Tile) map_.getSpriteAt(hq2.getX_(), hq2.getY_());
            t2.setBuildable_(hq2);
        }catch(Exception ignored){}

        frame1_ = new GameFrame("Medieval Conquest - Player Pedro", screenWidth, screenHeight, gamePanelHeight_, map_,
                new Player("Pedro", new Inventory(new Resources(200, 200, 200), new ArrayList<>()), hq1),
                removeBuildable -> removeBuildableFrom(frame1_.opponentBuildableToBeRemoved, frame2_.getPlayer()),
                endTurn -> processTurn()
        );

        frame1_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1_.setSize(new Dimension(screenWidth, screenHeight));
        frame1_.setResizable(false);
        frame1_.pack();
        frame1_.setVisible(true);

        frame2_ = new GameFrame("Medieval Conquest - Player Yusuf", screenWidth, screenHeight,gamePanelHeight_, map_,
                new Player("Yusuf", new Inventory(new Resources(200,200,200), new ArrayList<>()), hq2),
                removeBuildable -> removeBuildableFrom(frame2_.opponentBuildableToBeRemoved, frame1_.getPlayer()),
                endTurn -> processTurn()
        );

        // Player 2 starts waiting for player 1's turn.
        frame2_.getSouthPanel_().disableNewInteractions();

        // Setting alternative sprites for player 2
        frame2_.getSouthPanel_().useAlternativeSprites = true;

        frame2_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2_.setSize(new Dimension(screenWidth, screenHeight));
        frame2_.setResizable(false);
        frame2_.pack();
        frame2_.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame2_.setLocation(dim.width - frame2_.getSize().width, 0);
    }

    void processTurn(){
        if (hasEnded()){
            if (isPlayer1){
                JOptionPane.showMessageDialog(frame1_, "Congratulations, you've won!", "YOU WIN", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(frame2_, "Good luck next time!", "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frame2_, "Congratulations, you've won!", "YOU WIN", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(frame1_, "Good luck next time!", "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
            }
            // TODO: Close JFrames on closing event
            return;
        }

        isPlayer1 = !isPlayer1;

        // Begin other player's turn
        if (isPlayer1){
            turn_++;
            frame1_.getSouthPanel_().setTurn(turn_);
            frame1_.getSouthPanel_().startTurn();
            frame1_.repaintUI();
        }else{
            frame2_.getSouthPanel_().setTurn(turn_);
            frame2_.getSouthPanel_().startTurn();
            frame2_.repaintUI();
        }
    }

    void removeBuildableFrom(Buildable buildable, Player player){
        if (buildable != null){player.removeBuildable(buildable);}
    }

    public boolean hasEnded() {
        return frame1_.getPlayer().getHeadquarters().getHp_() <= 0 || frame2_.getPlayer().getHeadquarters().getHp_() <= 0;
    }
}
