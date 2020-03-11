package main.unit;

import main.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Soldier extends Unit {
    public Soldier(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(45, new Resources(60, 0, 20), x, y, width, height, null, 3, 6);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/sol/w1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/sol/w2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/sol.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }
    }
}
