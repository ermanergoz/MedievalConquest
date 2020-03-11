package main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Map{

    private Sprite[][] buffer_;
    private Image background_;
    private final int width_;
    private final int height_;
    private final int spriteWidth_ = 40;
    private final int spriteHeight_ = 40;

    public Map() {
        buffer_ = new Sprite[0][0];
        height_ = 600;
        width_ = 1200;
        background_ = (new ImageIcon(new File("res/images/background.png").getAbsolutePath())).getImage();
    }

    public Map(String fileName, int mapWidth, int mapHeight){
        height_ = mapHeight;
        width_ = mapWidth;
        background_ = (new ImageIcon(new File("res/images/background.png").getAbsolutePath())).getImage();
        loadMap(fileName);
    }

    public void loadMap(String fileName) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            ArrayList<ArrayList<Sprite>> map = new ArrayList<>();
            int i = 0, j = 0;

            for (String line = br.readLine(); line != null; line = br.readLine()){
                map.add(new ArrayList<>());
                String[] lineStrings = line.split(" ");
                for( String s : lineStrings) {
                    switch (s) {
                        case ("P"):
                            map.get(i).add(new Tile(Tile.TileType.PLAIN, j * spriteWidth_, i * spriteHeight_, spriteWidth_, spriteHeight_));
                            break;
                        case ("W"):
                            map.get(i).add(new Tile(Tile.TileType.WATER, j * spriteWidth_, i * spriteHeight_, spriteWidth_, spriteHeight_));
                            break;
                        case ("F"):
                            map.get(i).add(new Tile(Tile.TileType.FOREST, j * spriteWidth_, i * spriteHeight_, spriteWidth_, spriteHeight_));
                            break;
                        case ("B"):
                            map.get(i).add(new NeutralBazaar(j * spriteWidth_, i * spriteHeight_, spriteWidth_, spriteHeight_));
                            break;
                    }
                    j++;
                }
                i++;
                j = 0;
            }


            buffer_ = new Sprite[map.size()][];
            for (int k = 0; k < map.size(); k++) {
                ArrayList<Sprite> row = map.get(k);
                buffer_[k] = row.toArray(new Sprite[0]);
            }

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable read map from file.");
        }
    }

    public Image getBackground() {
        return background_;
    }

    public int getWidth() {
        return width_;
    }

    public int getHeight() {
        return height_;
    }

    public Sprite getSpriteAt(int x, int y){
        return buffer_[ y /spriteHeight_][x / spriteWidth_];
    }

    public Sprite[][] getBuffer_() {
        return buffer_;
    }

    public void setBuffer_(Sprite[][] buffer_) {
        this.buffer_ = buffer_;
    }
}
