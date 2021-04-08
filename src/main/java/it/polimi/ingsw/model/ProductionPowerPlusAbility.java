package it.polimi.ingsw.model;
/*Last Edit: William Zeni*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public class ProductionPowerPlusAbility implements Ability {

    private ResourceType typeOfRes;

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
        input.add(typeOfRes, 1);
        owner.getPersonalBoard().addExtraProduction(new ProductionPower(0, input, new NumberOfResources(), 1, 1));
    }
}