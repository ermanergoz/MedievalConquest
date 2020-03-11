package main.unit;

import main.Buildable;
import main.Resources;
import main.Tile;

import java.awt.*;

public abstract class Unit extends Buildable {

    // attributes
    protected int range_;
    protected int dmg_;
    protected int nMoves_;
    protected int initialNMoves_ = 1;

    // constructors
    public Unit() {
        super();
    }

    public Unit(int hp, Resources cost, int x, int y, int width, int height, Image[] images, int range, int dmg_) {
        super(hp, cost, x, y, width, height, images);
        setRange(range);
        setDmg_(dmg_);
        resetNMoves();
    }

    // class methods//
    public boolean move(Tile tile) {
        // If there are no more moves left, return false
        if (nMoves_ <= 0)
            return false;

        // Check if range_ of unit is enough to reach tile
        if (!(this instanceof Amphibious) && (tile.getType_() != Tile.TileType.PLAIN ||
                this.getRange() * tile.getWidth_() < (Math.abs(tile.getX_() - this.getX_()) + Math.abs(tile.getY_() - this.getY_())))) {
            return false;

        } else if (this instanceof Amphibious && (tile.getType_() == Tile.TileType.FOREST ||
                this.getRange() * tile.getWidth_() < (Math.abs(tile.getX_() - this.getX_()) + Math.abs(tile.getY_() - this.getY_())))) {
            return false; //if it is a amphibious
        }

        tile.setBuildable_(this);
        this.setX_(tile.getX_());
        this.setY_(tile.getY_());
        nMoves_--;
        return true;
    }

    public boolean attack(Buildable b) {
        // If there are no more moves left, return false
        if (nMoves_ <= 0)
            return false;

        // Check if range_ of unit is enough to reach buildable
        if (this.getRange() * b.getWidth_() < (Math.abs(b.getX_() - this.getX_()) + Math.abs(b.getY_() - this.getY_()))) {
            return false;
        }

        b.setHp_(b.getHp_() - this.dmg_);
        nMoves_--;
        return true;
    }


    // getters and setters
    public int getRange() {
        return range_;
    }

    public void setRange(int range) {
        this.range_ = range;
    }

    public int getDmg_() {
        return dmg_;
    }

    public void setDmg_(int dmg_) {
        this.dmg_ = dmg_;
    }

    public int getnMoves_() {
        return nMoves_;
    }

    public int getInitialNMoves() {
        return initialNMoves_;
    }

    public void setInitialNMoves(int initialNMoves) {
        this.initialNMoves_ = initialNMoves;
    }

    public void resetNMoves() {
        nMoves_ = initialNMoves_;
    }
}