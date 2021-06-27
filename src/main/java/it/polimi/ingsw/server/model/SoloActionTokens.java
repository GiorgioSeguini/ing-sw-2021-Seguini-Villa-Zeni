package it.polimi.ingsw.server.model;


/**
 * The interface Solo action tokens.
 */
public interface SoloActionTokens{

    /**
     * Activate token.
     *
     * @param game of type GameExt: the game.
     */
    void ActivateToken(GameExt game);

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets enum.
     *
     * @return the enum
     */
    String getEnum();
}