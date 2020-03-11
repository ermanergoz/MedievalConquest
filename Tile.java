package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tile extends Sprite {
    public enum TileType {
        PLAIN, FOREST, WATER
    }

    private TileType type_;

    // Reference to a buildable which is located on top of this tile, if there is one; null, otherwise.
    private Buildable buildable_ = null;

    Tile(TileType type, int x, int y, int width, int height) {
        super(x, y, width, height, null);
        Image image = null;
        switch (type){
            case PLAIN:
                image = (new ImageIcon(new File("res/images/plainTile.png").getAbsolutePath())).getImage();
                break;
            case WATER:
                image = (new ImageIcon(new File("res/images/waterTile.png").getAbsolutePath())).getImage();
                break;
            case FOREST:
                image = (new ImageIcon(new File("res/images/forestTile.png").getAbsolutePath())).getImage();
                break;
        }

        if (image != null){
            setImages_(new Image[]{image});
        }

        this.type_ = type;
    }

    Tile() {
        super();
        this.type_ = TileType.PLAIN;
    }


    public Buildable getBuildable_() {
        return buildable_;
    }

    public void setBuildable_(Buildable buildable_) {
        this.buildable_ = buildable_;
    }

    public TileType getType_() {
        return type_;
    }

    public void setType_(TileType type) { type_ = type; }
}
