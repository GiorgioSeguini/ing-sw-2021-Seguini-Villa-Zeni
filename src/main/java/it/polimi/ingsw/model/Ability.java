package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public interface Ability {

    /*method that has to be implemented*/
    void RunAbility(Player owner);
    ResourceType getTypeOfRes();

}