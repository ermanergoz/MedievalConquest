package main.unit;

import main.Player;
import main.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Cavalry extends Unit {
    public Cavalry(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(120, new Resources(60, 0, 75), x, y, width, height, null, 4, 12);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/cav/w1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/cav/w2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/cav.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }
    }
}
