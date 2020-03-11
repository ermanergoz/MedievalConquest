package main.unit;

import main.Resources;
import main.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TreeCutter extends Unit {
    public TreeCutter(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(25, new Resources(15, 20, 0), x, y, width, height, null, 3, 3);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/trc/w1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/trc/w2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/trc.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }
    }

    public boolean cutTree(Tile tile) {
        // Check if tile is of type forest and if range_ of unit is enough to reach tile
        if (
                tile.getType_() != Tile.TileType.FOREST ||
                        this.getRange() * tile.getWidth_() < (Math.abs(tile.getX_() - this.getX_()) + Math.abs(tile.getY_() - this.getY_()))
        ) {
            return false;
        }

        tile.setType_(Tile.TileType.PLAIN);
        tile.setImages_(new Image[]{(new ImageIcon(new File("res/images/plainTile.png").getAbsolutePath())).getImage()});

        return true;
    }
}
