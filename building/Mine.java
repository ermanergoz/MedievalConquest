package main.building;

import main.Buildable;
import main.Player;
import main.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Mine extends Buildable {

    public Mine(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(1000, new Resources(0, 100, 0), x, y, width, height, null);
        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/mine1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/mine2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/mine.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }

    }


    private void incResource() {
        //TODO: incRes
    }


}

