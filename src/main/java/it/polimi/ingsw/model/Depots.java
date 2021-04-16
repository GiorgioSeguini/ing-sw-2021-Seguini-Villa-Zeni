package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillException;

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

    /**
     * @param input 
     * @return
     */
    public void addResourceFromProduction(NumberOfResources input) {
        strongBox.addResource(input);
    }

    /**
     *
     * @param input
     * @throws IllegalArgumentException
     */
    public void addResourcesFromMarket(NumberOfResources input) throws UnableToFillException {
        wareHouseDepots.addResource(input);
    }

    /**
     * @param required 
     * @return
     */
    public void subResource(NumberOfResources required) throws OutOfResourcesException {
        try {
            wareHouseDepots.subResource(required);
        }catch(OutOfResourcesException e){
            //remove as match resources possible from the warehouse depots
            NumberOfResources missing = required.safe_sub(wareHouseDepots.getResources());
            NumberOfResources subtracted = new NumberOfResources();
            try {
                subtracted = required.sub(missing);
            }catch(OutOfResourcesException ignored){}

            strongBox.subResource(missing);        //it can throws an exception but in this case I just rethrow it, I can't do anything differently

            //if no exception, I need to really update wareHouseDepots
            wareHouseDepots.subResource(subtracted);

        }
    }

    /**
     * @param required 
     * @return
     */
    public boolean match(NumberOfResources required) {
        NumberOfResources currentResources = getResources();
        try{
            currentResources.sub(required);
        }catch(OutOfResourcesException e){
            return false;
        }
        return true;
    }

    /**
     * @param input 
     * @return
     */
    /*
    public boolean canAddFromMarket(NumberOfResources input) {
        return wareHouseDepots.canAdd(input);
    }
*/
    /**
     *
     * @param s
     */
    public void addExtraShelf(Shelf s){
        wareHouseDepots.addExtraShelf(s);
    }

    public int getVictoryPoints(){
        return this.getResources().size()/DIVIDER_VICTORY_POINTS;
    }

    /*just for testing*/
    public WareHouseDepots getWareHouseDepots() {
        return wareHouseDepots;
    }
}