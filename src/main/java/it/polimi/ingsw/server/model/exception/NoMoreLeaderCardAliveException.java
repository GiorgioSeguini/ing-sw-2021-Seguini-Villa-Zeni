package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

/**
 * The type No more leader card alive exception.
 */
public class NoMoreLeaderCardAliveException extends Exception{
    private final ErrorMessage errorMessage;

    /**
     * Instantiates a new No more leader card alive exception.
     */
    public NoMoreLeaderCardAliveException() {
        errorMessage = ErrorMessage.NoMoreLeaderCardAliveError;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
