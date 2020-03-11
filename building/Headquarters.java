package main.building;

import main.Buildable;
import main.Player;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Headquarters extends Buildable {

    public Headquarters(int x, int y, int width, int height, boolean useAlternativeSprites) {
        super(2500, null, x, y, width, height, null);

        Image image,image2,avatar;
        // Choose between original and alternative sprites

        image = (new ImageIcon(new File("res/images/hq1.png").getAbsolutePath())).getImage();
        image2 = (new ImageIcon(new File("res/images/hq2.png").getAbsolutePath())).getImage();
        avatar = (new ImageIcon(new File("res/images/avt/hq.png").getAbsolutePath())).getImage();
        if (!useAlternativeSprites){
            setImages_(new Image[]{image,avatar});

        }else{
            setImages_(new Image[]{image2,avatar});
        }

    }
}
