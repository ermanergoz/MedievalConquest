package main.unit;

import main.Player;
import main.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Archer extends Unit {
    public Archer(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(30, new Resources(0,25,45), x, y, width, height, null, 3, 4);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/arc/w1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/arc/w2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/arc.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }
    }
}
