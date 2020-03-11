package main;

import java.awt.*;

public abstract class Sprite {

    // attributes
    private int x_;
    private int y_;
    private int width_;
    private int height_;
    private Image[] images_;

    // constructor
    Sprite() {
        setX_(0);
        setY_(0);
        setWidth_(0);
        setHeight_(0);
        setImages_(null);
    }

    Sprite(int x, int y, int width, int height, Image[] images) {
        setX_(x);
        setY_(y);
        setWidth_(width);
        setHeight_(height);
        setImages_(images);
    }

    // draw method
    void draw(Graphics g) {
        // For now, there's only one image per sprite
        if (images_ != null && images_[0] != null){
            g.drawImage(images_[0], x_, y_, width_, height_, null);
        }
    }

    // getters and setters
    public int getX_() {
        return x_;
    }

    public void setX_(int x_) {
        this.x_ = x_;
    }

    public int getY_() {
        return y_;
    }

    public void setY_(int y_) {
        this.y_ = y_;
    }

    public int getWidth_() {
        return width_;
    }

    public void setWidth_(int width_) {
        this.width_ = width_;
    }

    public int getHeight_() {
        return height_;
    }

    public void setHeight_(int height_) {
        this.height_ = height_;
    }

    public Image[] getImages_() {
        return images_;
    }

    public void setImages_(Image[] images_) {
        this.images_ = images_;
    }
}