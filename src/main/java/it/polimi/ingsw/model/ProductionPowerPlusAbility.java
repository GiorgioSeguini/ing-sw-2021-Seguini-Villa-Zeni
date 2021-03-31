package it.polimi.ingsw.model;
/*Last Edit: William Zeni*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public class ProductionPowerPlusAbility extends Ability {

    private ResourceType typeOfRes;

    /* Default constructor*/
    ProductionPowerPlusAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
    }

    /*Abstract class to implement*/
    public void RunAbility(Player owner) {
        NumberOfResources input = new NumberOfResources();
        input.add(typeOfRes, 1);
        owner.getPersonalBoard().addExtraProduction(new ProductionPower(0, input, new NumberOfResources(), 1, 1));
    }
}