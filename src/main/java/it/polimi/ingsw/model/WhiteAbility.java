package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public class WhiteAbility extends Ability {

    private ResourceType typeOfRes;

    /*Default constructor*/
    WhiteAbility(ResourceType type) {
        this.typeOfRes=type;
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    /*Abstract class to implement*/
    public void RunAbility(Player owner) {
        owner.getConverter().setWhiteAbility();
    }
}