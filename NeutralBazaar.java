package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class NeutralBazaar extends Sprite {

    public NeutralBazaar(int x, int y, int width, int height) {

        super(x, y, width, height, null);

        Image image = (new ImageIcon(new File("res/images/neutralBazaar.png").getAbsolutePath())).getImage();
        Image avatar = (new ImageIcon(new File("res/images/avt/bzr.png").getAbsolutePath())).getImage();


        setImages_(new Image[]{image,avatar});
    }

    //TODO neutral bazaar hp scale
}
