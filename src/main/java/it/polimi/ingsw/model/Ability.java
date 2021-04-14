package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ResourceType;

public interface Ability {

    /*method that has to be implemented*/
    public void RunAbility(Player owner);
    public ResourceType getTypeOfRes();

}