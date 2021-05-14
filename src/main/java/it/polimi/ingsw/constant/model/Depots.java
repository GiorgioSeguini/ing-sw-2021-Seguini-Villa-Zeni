package it.polimi.ingsw.constant.model;
/*Last Edit: Gio*/

/**
 * 
 */
public class Depots {
    private static final int DIVIDER_VICTORY_POINTS = 5;
    private StrongBox strongBox;
    private WareHouseDepots wareHouseDepots;
    /**
     * Default constructor
     */
    public Depots() {
        strongBox = new StrongBox();
        wareHouseDepots = new WareHouseDepots();
    }


    /**
     *
     *@return total resources stored in the depots ( both Strongbox and WareHouse)
     */
    public NumberOfResources getResources() {
        NumberOfResources fromStrongBox = strongBox.getResources();
        NumberOfResources fromWareHouse = wareHouseDepots.getResources();
        return fromStrongBox.add(fromWareHouse);
    }



    public int getVictoryPoints(){
        return this.getResources().size()/DIVIDER_VICTORY_POINTS;
    }

    public WareHouseDepots getWareHouseDepots() {
        return wareHouseDepots;
    }

    public StrongBox getStrongBox(){
        return strongBox;
    }

    public void setStrongBox(StrongBox strongBox) {
        this.strongBox = strongBox;
    }

    public void setWareHouseDepots(WareHouseDepots wareHouseDepots) {
        this.wareHouseDepots = wareHouseDepots;
    }

    @Override
    public String toString(){
        String res="------------\n";
        res+="| Depots |\n";
        res+="------------\n";
        res+=this.getStrongBox();
        res+="\n";
        res+=this.getWareHouseDepots();
        res+="\n";
        return res;
    }
}