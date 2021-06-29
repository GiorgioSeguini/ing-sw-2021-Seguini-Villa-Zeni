package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ResourceType;

/**
 * WhiteAbility class.
 * Implements Ability interface.
 * WhiteAbility: add the conversion of white Marbles in the specified ResourceType
 */
public class WhiteAbility implements Ability {

    private final ResourceType typeOfRes;
    public static final String name="WhiteAbility";

    /**
     *
     * @return of type String: the ability's name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Instantiates a new White ability.
     *
     * @param type of type ResourceType: the ability's resource type.
     */
    /*Default constructor*/
    public WhiteAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /**
     * Getter.
     *
     * @return of type ResourceType: the ability's resource type.
     */
    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    /**
     * Set the white ability active.
     *
     * @param owner of type PlayerExt: the ability's owner.
     */
    @Override
    public void RunAbility(PlayerExt owner) {
        owner.getConverter().setWhiteAbility(typeOfRes);
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o is equals to this. False if param o isn't an instance of WhiteAbility or o isn't equals to this.
     */
    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof WhiteAbility))
            return false;

        WhiteAbility other = (WhiteAbility) o;

        return other.typeOfRes.equals(this.typeOfRes);
    }
}