package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

/**
 * The type Have to choose exception.
 */
public class HaveToChooseException extends Exception{
    private final ErrorMessage errorMessage;

    /**
     * Instantiates a new Have to choose exception.
     */
    public HaveToChooseException() {
        errorMessage = ErrorMessage.HaveToChooseError;
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
