package main;

import main.unit.Unit;
import javax.swing.*;

public class Item {
    private String description_;
    private Resources cost_;
    private Icon icon_;
    private Player player_;

    public Item(String description, Resources cost, String imageName, Player player_) {
        this.description_ = description;
        this.cost_ = cost;
        this.icon_ = new ImageIcon("res/images/itm/" + imageName);
        this.player_ = player_;
    }

    public void applyPerks(String description_) {
        System.out.println(description_);
        switch (description_) {
            case ("Health Potion"):
                for (Buildable buildable : player_.getBuildables_()) {
                    if (buildable.getHp_() < 25)
                        buildable.setHp_(buildable.getHp_() + 10);
                }
                break;
            case ("Iron Sword"):
                for (Buildable buildable : player_.getBuildables_()) {
                    if (buildable instanceof Unit)
                        ((Unit) buildable).setDmg_(((Unit) buildable).getDmg_() + 5);
                }
                break;
            case ("Sword of the Gods"):
                for (Buildable buildable : player_.getBuildables_()) {
                    if (buildable instanceof Unit)
                        ((Unit) buildable).setDmg_(((Unit) buildable).getDmg_() + 20);
                }
                break;
            case ("Shield"):
                for (Buildable buildable : player_.getBuildables_()) {
                    if (buildable instanceof Unit)
                        buildable.setHp_(buildable.getHp_() + 20);
                }
                break;
            case ("Compass"):
                for (Buildable buildable : player_.getBuildables_()) {
                    if (buildable instanceof Unit)
                        ((Unit) buildable).setRange(((Unit) buildable).getRange() + 3);
                }
                break;
            case ("Arms of Morpheus"): // Increases the amount of moves for every active unit
                for (Buildable buildable : player_.getBuildables_())
                    if (buildable instanceof Unit){
                        Unit unit = (Unit) buildable;
                        unit.setInitialNMoves(unit.getInitialNMoves() + 1);
                    }

                break;
        }
    }

    public String getDescription() {
        return description_;
    }

    public void setDescription(String description_) {
        this.description_ = description_;
    }

    public Resources getCost() {
        return cost_;
    }

    public void setCost(Resources cost_) {
        this.cost_ = cost_;
    }

    public Icon getImage() {
        return icon_;
    }

    public void setImage(Icon icon_) {
        this.icon_ = icon_;
    }
}
