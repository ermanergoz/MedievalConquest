package main;

import java.util.ArrayList;

public class Inventory {
    private Resources resources_;
    private ArrayList<Item> items_;

    public Inventory(Resources resources, ArrayList<Item> items) {
        this.resources_ = resources;
        this.items_ = items;
    }

    public Inventory() {
        this.resources_ = new Resources();
        this.items_ = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items_.add(item);
    }

    public void removeItem(Item item) {
        this.items_.remove(item);
    }

    public void resetItems()
    {
        this.items_.clear();
    }

    public Resources getResources_() {
        return resources_;
    }

    public void setResources_(Resources resources) {this.resources_ = resources;}

    public ArrayList<Item> getItems_() {
        return items_;
    }
}
