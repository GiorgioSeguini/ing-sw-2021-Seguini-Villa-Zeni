package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.server.model.enumeration.ResourceType;

public interface Ability {

    /*method that has to be implemented*/
    void RunAbility(Player owner);
    ResourceType getTypeOfRes();

}