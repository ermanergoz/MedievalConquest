package main;

import java.awt.*;

public abstract class Buildable extends Sprite {

    // attributes
    protected int hp_;
    protected Resources cost;

    // constructors
    public Buildable() {
        super();
        setHp_(0);
        setCost(null);
    }

    public Buildable(int hp, Resources cost, int x, int y, int width, int height, Image[] images) {
        super(x, y, width, height, images);
        setHp_(hp);
        setCost(cost);
    }

    // getters and setters
    public int getHp_() {
        return hp_;
    }

    public void setHp_(int hp_) {
        this.hp_ = hp_;
    }

    public Resources getCost() {
        return cost;
    }

    public void setCost(Resources cost) {
        this.cost = cost;
    }
}
