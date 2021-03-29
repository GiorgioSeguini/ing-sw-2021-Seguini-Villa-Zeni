package it.polimi.ingsw.model;

/*Last Edit: Gio*/

/**
 * 
 */
public class Depots {

    /**
     * Default constructor
     */
    public Depots() {
    }
    private StrongBox strongBox;
    private WareHouseDepots wareHouseDepots;

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
    public void addResourcesFromMarket(NumberOfResources input) throws UnableToFillError{
        wareHouseDepots.addResource(input);
    }

    /**
     * @param required 
     * @return
     */
    public void subResource(NumberOfResources required) throws UnableToFillError {
        try {
            wareHouseDepots.subResource(required);
        }catch(IllegalArgumentException e){
            //remove as match resources possible from the warehouse depots
            NumberOfResources missing = required.clone();
            NumberOfResources subtracted = new NumberOfResources();
            NumberOfResources currentResources = wareHouseDepots.getResources();

            for(ResourceType type: ResourceType.values()){
                int toRemove=0;
                if(currentResources.getAmountOf(type) < missing.getAmountOf(type)){
                    //remove all the resources of type
                    toRemove = currentResources.getAmountOf(type);
                }
                else{
                    //remove only the required resources
                    toRemove = missing.getAmountOf(type);
                }
                currentResources = currentResources.sub(type, toRemove);
                subtracted = subtracted.add(type, toRemove);
                missing = missing.sub(type, toRemove);
            }
            strongBox.subResource(required);        //it can throwws an xception but in this case I just rethrow it, I can't do anything differently

            //if no exception, i need to really update wareHouseDepots
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
        }catch(IllegalArgumentException e){
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
}