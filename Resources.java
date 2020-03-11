package main;

public class Resources {
    private int nIron_ = 0;
    private int nWood_ = 0;
    private int nGold_ = 0;

    public Resources() {
    }

    public Resources(int nIron, int nWood, int nGold) {
        nIron_ = nIron;
        nWood_ = nWood;
        nGold_ = nGold;
    }

    boolean isLessOrEqualTo(Resources resources){
        return (this.nGold_ <= resources.nGold_) || (this.nIron_ <= resources.nIron_) || (this.nWood_ <= resources.nWood_);
    }

    Resources minus(Resources resources){
        return new Resources(this.nIron_ - resources.nIron_, this.nWood_ - resources.nWood_, this.nGold_ - resources.nGold_);
    }

    public int getnIron_() {
        return nIron_;
    }

    public void setnIron_(int nIron_) {
        this.nIron_ = nIron_;
    }

    public int getnWood_() {
        return nWood_;
    }

    public void setnWood_(int nWood_) {
        this.nWood_ = nWood_;
    }

    public int getnGold_() {
        return nGold_;
    }

    public void setnGold_(int nGold_) {
        this.nGold_ = nGold_;
    }

    @Override
    public String toString() {
        return "Resources: " +
                "Iron" + nIron_ +
                ", Wood" + nWood_ +
                ", Gold" + nGold_ +
                '.';
    }
}
