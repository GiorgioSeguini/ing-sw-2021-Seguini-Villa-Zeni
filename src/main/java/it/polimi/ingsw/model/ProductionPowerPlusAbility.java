package it.polimi.ingsw.model;
/*Last Edit: William Zeni*/

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

    }
}