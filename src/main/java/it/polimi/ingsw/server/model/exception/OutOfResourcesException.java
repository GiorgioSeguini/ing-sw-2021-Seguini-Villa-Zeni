package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

/**
 * The type Out of resources exception.
 */
public class OutOfResourcesException extends Exception{
    private final ErrorMessage errorMessage;

    /**
     * Instantiates a new Out of resources exception.
     */
    public OutOfResourcesException() {
        errorMessage = ErrorMessage.OutOfResourcesError;
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
