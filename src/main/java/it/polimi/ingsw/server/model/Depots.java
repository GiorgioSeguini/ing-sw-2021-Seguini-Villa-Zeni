package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.constant.message.DepotsMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import it.polimi.ingsw.server.observer.Observable;

/**
 * 
 */
public class Depots extends Observable<Message> {

    private static final int DIVIDER_VICTORY_POINTS = 5;

    private final int ownerID;
    private final StrongBox strongBox;
    private final WareHouseDepots wareHouseDepots;
    /**
     * Default constructor
     */
    public Depots(int ownerID) {
        this.ownerID=ownerID;
        strongBox = new StrongBox();
        wareHouseDepots = new WareHouseDepots();
    }


    /**
     *
     * @return total resources stored in the depots ( both Strongbox and WareHouse)
     */
    public NumberOfResources getResources() {
        NumberOfResources fromStrongBox = strongBox.getResources();
        NumberOfResources fromWareHouse = wareHouseDepots.getResources();
        return fromStrongBox.add(fromWareHouse);
    }

    /**
     * @param input resources to add
     */
    public void addResourceFromProduction(NumberOfResources input) {
        strongBox.addResource(input);
        change();
    }

    /**
     *
     * @param input resources to add
     * @throws IllegalArgumentException if you can't add this resources
     */
    public void addResourcesFromMarket(NumberOfResources input) throws UnableToFillException {
        wareHouseDepots.addResource(input);
        change();
    }

    /**
     * @param required number of resources to subtract
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
        change();
    }

    /**
     * @param required resources needed
     * @return true if the depots contains enough resources
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

    /*
    public boolean canAddFromMarket(NumberOfResources input) {
        return wareHouseDepots.canAdd(input);
    }
*/
    /**
     * @param s shelf to add
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

    /**
     * This methods create an instance of FaithTrackMessage and notify observers
     */
    private void change(){
        String depots = "";
        //TODO parsing

        notify(new DepotsMessage(depots, this.ownerID));
    }
}