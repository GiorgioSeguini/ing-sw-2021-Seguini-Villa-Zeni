package it.polimi.ingsw.server.model;
/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

public class ProductionPowerPlusAbility implements Ability {

    private final ResourceType typeOfRes;

    /* Default constructor*/
    public ProductionPowerPlusAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
    }

    @Override
    public void RunAbility(Player owner) {
        NumberOfResources input = new NumberOfResources();
        input=input.add(typeOfRes, 1);
        owner.getPersonalBoard().addExtraProduction(new ProductionPower(0, input, new NumberOfResources(), 1, 1));
    }

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