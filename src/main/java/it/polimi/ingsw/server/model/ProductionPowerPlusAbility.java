package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.ProductionPower;

/**
 * ProductionPowerPlusAbility.
 * Implements Ability.
 * PowerProductionPlusAbility: add an extra production that take 1 Resource of the specified ResourceType and get 1 faith points and 1 of choice resource
 */
public class ProductionPowerPlusAbility implements Ability {

    private final ResourceType typeOfRes;
    public static final String name="ProductionPowerPlusAbility";


    /**
     * Instantiates a new Production power plus ability.
     *
     * @param type of type ResourceType: the resource type.
     */
    /* Default constructor*/
    public ProductionPowerPlusAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /**
     *
     * @return of type ResourceType: the resource type.
     */
    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
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
     * Active the production power plus ability.
     *
     * @param owner of type PlayerExt: the ability's owner.
     */
    @Override
    public void RunAbility(PlayerExt owner) {
        NumberOfResources input = new NumberOfResources();
        input=input.add(typeOfRes, 1);
        owner.getPersonalBoard().addExtraProduction(new ProductionPower(1, new NumberOfResources(), input, 0, 1));
    }

    /**
     *
     * @param o of type Object.
     * @return True if param o is equals to this. False if param o isn't an instance of ProductionPowerPlusAbility or o isn't equals to this.
     */
    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;

        if(!(o instanceof ProductionPowerPlusAbility))
            return false;

        ProductionPowerPlusAbility other = (ProductionPowerPlusAbility) o;

        return other.typeOfRes.equals(this.typeOfRes);
    }
}