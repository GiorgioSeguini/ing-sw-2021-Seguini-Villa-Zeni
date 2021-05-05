package it.polimi.ingsw.server.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

public interface Ability {
    /*method that has to be implemented*/
    void RunAbility(Player owner);
    ResourceType getTypeOfRes();

    public abstract String getName();

}