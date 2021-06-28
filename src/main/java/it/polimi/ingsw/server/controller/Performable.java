package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.GameExt;

/**
 * The interface Performable.
 */
public interface Performable {

    /**
     * Perform move method.
     *
     * @param game of type GameExt: the game
     */
    void performMove(GameExt game);

    /**
     * Can perform ext boolean.
     * Check if the player can really make the move.
     * @param game of type GameExt: the game
     * @return the boolean
     */
    boolean canPerformExt(GameExt game);

    /**
     * Gets id player.
     *
     * @return of type int: the player's id.
     */
    int getIdPlayer();
}
