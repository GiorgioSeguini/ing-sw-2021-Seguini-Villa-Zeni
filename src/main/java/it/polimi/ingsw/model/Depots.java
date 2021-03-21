package it.polimi.ingsw.model;

import java.util.*;

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

    public void addResourcesFromMarket(NumberOfResources input) throws Exception{
        wareHouseDepots.addResource(input);
    }

    /**
     * @param required 
     * @return
     */
    public void subResource(NumberOfResources required) {
        try {
            wareHouseDepots.subResource(required);
        }catch(IllegalArgumentException e){
            //TODO
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
    public boolean canAddFromMarket(NumberOfResources input) {
        return wareHouseDepots.canAdd(input);
    }

}