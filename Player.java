package main;

import main.building.Headquarters;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name_;
    private Inventory inventory_;
    private ArrayList<Buildable> buildables_;

    public Player(String name, Inventory inventory) {
        this.name_ = name;
        this.inventory_ = inventory;
        this.buildables_ = new ArrayList<>();
    }

    public Player(String name, Inventory inventory, Headquarters hq) {
        this.name_ = name;
        this.inventory_ = inventory;
        this.buildables_ = new ArrayList<>(Arrays.asList(hq));
    }

    public Inventory getInventory_() {
        return inventory_;
    }

    public void setInventory_(Inventory inventory_) {
        this.inventory_ = inventory_;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public ArrayList<Buildable> getBuildables_() {
        return buildables_;
    }

    public boolean isOwnerOf(Buildable b){
        boolean l = false;
        int i = 0;
        while (!l && i < buildables_.size()){
            l = b.getX_() == buildables_.get(i).getX_() && b.getY_() == buildables_.get(i).getY_();
            i++;
        }
        return l;
    }

    public void addBuildable(Buildable b){
        this.buildables_.add(b);
    }

    public boolean removeBuildable(Buildable b){
        if (!this.buildables_.contains(b)){
            return false;
        }

        this.buildables_.remove(b);
        return true;
    }

    public Headquarters getHeadquarters(){
        Headquarters hq = null;

        for(Buildable b : this.buildables_){
            if (b instanceof Headquarters){
                hq = (Headquarters) b;
                break;
            }
        }

        return hq;
    }
}
