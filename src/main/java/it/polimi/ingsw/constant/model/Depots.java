package it.polimi.ingsw.constant.model;

/**
 * Depots class.
 * Superclass of DepotsExt (Server).
 */
public class Depots {
    private static final int DIVIDER_VICTORY_POINTS = 5;
    private StrongBox strongBox;
    private WareHouseDepots wareHouseDepots;

    /**
     * Default constructor.
     */
    public Depots() {
        strongBox = new StrongBox();
        wareHouseDepots = new WareHouseDepots();
    }


    /**
     * Gets resources.
     *
     * @return of type NumberOfResources: total resources stored in the depots ( both Strongbox and WareHouse)
     */
    public NumberOfResources getResources() {
        NumberOfResources fromStrongBox = strongBox.getResources();
        NumberOfResources fromWareHouse = wareHouseDepots.getResources();
        return fromStrongBox.add(fromWareHouse);
    }


    /**
     * Get victory points from depots.
     *
     * @return of type int: number of victory points from depots.
     */
    public int getVictoryPoints(){
        return this.getResources().size()/DIVIDER_VICTORY_POINTS;
    }

    /**
     * Gets the ware house depots.
     *
     * @return of type WareHouseDepots: the ware house depots.
     */
    public WareHouseDepots getWareHouseDepots() {
        return wareHouseDepots;
    }

    /**
     * Gets the strong box.
     *
     * @return of type StrongBox: the strong box
     */
    public StrongBox getStrongBox(){
        return strongBox;
    }

    /**
     * Sets strong box.
     *
     * @param strongBox of type StrongBox: the strong box to set.
     */
    public void setStrongBox(StrongBox strongBox) {
        this.strongBox = strongBox;
    }

    /**
     * Sets ware house depots.
     *
     * @param wareHouseDepots of type WareHouseDepots: the ware house depots to set.
     */
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