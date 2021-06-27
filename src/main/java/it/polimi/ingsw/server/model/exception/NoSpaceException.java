package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

/**
 * The type No space exception.
 */
public class NoSpaceException extends Exception{
    private final ErrorMessage errorMessage;

    /**
     * Instantiates a new No space exception.
     */
    public NoSpaceException() {
        errorMessage = ErrorMessage.NoSpaceError;
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
