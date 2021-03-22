package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

public class White extends Ability {

    private ResourceType typeOfRes;

    /*Default constructor*/
    White() {
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    /*Abstract class to implement*/
    public void RunAbility(Player owner) {

    }
}