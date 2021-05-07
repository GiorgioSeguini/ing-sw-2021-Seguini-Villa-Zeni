package it.polimi.ingsw.client.modelClient;
/*Last Edit: Gio*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

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
     * @return
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

    @Override
    public String toString(){
        String resSB="";
        String resWH="";
        resSB += "In the StrongBox: \n";
        for(ResourceType x: ResourceType.values()){
            resSB += "\t"+x+": "+strongBox.getResources().getAmountOf(x)+ "\n";
        }
        resWH += "In the WarehouseDepots: \n";
        for(ResourceType x: ResourceType.values()){
            resWH += "\t"+x+": "+wareHouseDepots.getResources().getAmountOf(x)+ "\n";
        }
        return resSB+resWH;
    }
}