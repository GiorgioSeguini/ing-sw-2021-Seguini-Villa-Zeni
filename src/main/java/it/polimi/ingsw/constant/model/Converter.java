package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.util.ArrayList;

/**
 * Converter class.
 * Superclass of ConverterExt.
 */
public class Converter {

    private final int ownerId;
    private NumberOfResources inwait;
    private final ArrayList<ResourceType> toconvert;
    private int white;

    /**
     * Instantiates a new Converter.
     *
     * @param ownerId of type int: the owner id.
     */
    /*Default Constructor*/
    public Converter(int ownerId){
        this.toconvert=new ArrayList<>();
        this.ownerId=ownerId;
        this.inwait=new NumberOfResources();
        this.white=0;
    }

    /*Getter*/

    /**
     * Gets toconvert.
     *
     * @return of type ArrayList<ResourceType>: the array list which contains the possible conversions.
     */
    public ArrayList<ResourceType> getToconvert() {
        return toconvert;
    }

    /**
     * Add toconvert.
     *
     * @param type of type ResourceType: the resource type yhe player want to add to the list.
     */
    public void addToconvert(ResourceType type){
        this.toconvert.add(type);
        if(toconvert.size()>2) throw new IllegalArgumentException();
    }

    /**
     * This method returns the Resources stored in Converter.
     * @return of type NumberOfResources: the number of resources
     */
    public NumberOfResources getResources(){
       return inwait;
    }

    /**
     * Gets owner id.
     *
     * @return of type int: the owner id.
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Set resources.
     *
     * @param resources of type NumberOfResources: resources the player want to set.
     */
    public void setResources(NumberOfResources resources){
      this.inwait=resources;
    }

    /**
     * Check if white ability is active.
     *
     * @return of type boolean: True if white ability is active. Otherwise False.
     */
    public boolean isWhiteAbilityActive(){
        return toconvert.size()>0;
    }

    /**
     * Gets the number of white marbles stored in the converter.
     *
     * @return of type int: the number.
     */
    public int getWhite() {
        return white;
    }

    /**
     * Sets the number of white marbles in the converter.
     *
     * @param white of type int: the number.
     */
    public void setWhite(int white) {
        this.white = white;
    }
}
