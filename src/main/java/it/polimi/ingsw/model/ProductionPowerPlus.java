package it.polimi.ingsw.model;
/*Last Edit: William Zeni*/

public class ProductionPowerPlus extends Ability {

    private ResourceType typeOfRes;

    /* Default constructor*/
    ProductionPowerPlus(ResourceType type) {
        this.typeOfRes=type;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return typeOfRes;
    }

    /*Abstract class to implement*/
    public void RunAbility() {

    }
}