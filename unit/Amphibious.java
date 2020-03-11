package main.unit;

import main.Player;
import main.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Amphibious extends Unit {
    public Amphibious(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(100, new Resources(0, 1, 1), x, y, width, height, null, 4, 35);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/amp/w1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/amp/w2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/amp.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }
    }

}
