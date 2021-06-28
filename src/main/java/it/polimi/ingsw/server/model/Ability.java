package it.polimi.ingsw.server.model;


import it.polimi.ingsw.constant.enumeration.ResourceType;

/**
 * The interface Ability.
 */
public interface Ability {

    /**
     * Run ability.
     *
     * @param owner the owner
     */
    /*method that has to be implemented*/
    void RunAbility(PlayerExt owner);

    /**
     * Gets type of res.
     *
     * @return the type of res
     */
    ResourceType getTypeOfRes();

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

}