package main.building;

import main.Buildable;
import main.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Machinery extends Buildable {
    public Machinery(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(2000, new Resources(20, 130, 0), x, y, width, height, null);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/mac1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/mac2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/mac.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }

    }
}
