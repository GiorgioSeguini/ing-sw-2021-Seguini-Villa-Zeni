package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

/**
 * The type Unable to fill exception.
 */
public class UnableToFillException extends Exception{
    private final ErrorMessage errorMessage;

    /**
     * Instantiates a new Unable to fill exception.
     */
    public UnableToFillException(){
        errorMessage = ErrorMessage.UnableToFillError;
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
