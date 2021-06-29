package it.polimi.ingsw.server.model;


import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.Shelf;

/**
 * The type Depots ability.
 * Implements Ability interface.
 * DepotsAbility: add an extra shelf of size 2 of the specified ResourceType
 */
public class DepotsAbility implements Ability {

    public static final int extraSize = 2;
    private final ResourceType typeOfRes;
    public static final String name="DepotsAbility";

    /**
     * Instantiates a new Depots ability.
     *
     * @param typeOfRes the type of res corresponding to the ability.
     */
    /*Default constructor*/
    public DepotsAbility(ResourceType typeOfRes) {
        this.typeOfRes=typeOfRes;
    }

    /**
     *
     * @return of type ResourceType: the resource type.
     */
    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    /**
     *
     * @return of type String: the name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Instantiates a new extra Shelf.
     * @param owner the owner's ability.
     */
    @Override
    public void RunAbility(PlayerExt owner){
        Shelf shelf =  new Shelf(extraSize);
        shelf.setResType(typeOfRes);
        owner.getDepots().addExtraShelf(shelf);
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o  is equals to this. False if param o isn't an instance of DepotsAbility or o isn't equals to this.
     */
    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof DepotsAbility))
            return false;

        DepotsAbility other = (DepotsAbility) o;

        return other.typeOfRes.equals(this.typeOfRes);
    }

}