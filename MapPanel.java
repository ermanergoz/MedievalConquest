package main;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private Map map_;

    public MapPanel(Map map){
        this.map_ = map;
    }

    public Map getMap() {
        return map_;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(map_.getBackground(), 0,0, map_.getWidth(), map_.getHeight(), null);
        for (Sprite[] row : map_.getBuffer_()){
            for(Sprite s : row){
                s.draw(graphics);
                try{
                    Tile tile = (Tile) s;
                    tile.getBuildable_().draw(graphics);
                }catch (Exception ignored){}
            }
        }
    }
}
