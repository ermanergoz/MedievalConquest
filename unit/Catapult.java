package main.unit;

import main.Player;
import main.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Catapult extends Unit {
    public Catapult(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(50, new Resources(0,160,135), x, y, width, height, null, 1, 40);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/cat/w1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/cat/w2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/cat.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }
    }
}
