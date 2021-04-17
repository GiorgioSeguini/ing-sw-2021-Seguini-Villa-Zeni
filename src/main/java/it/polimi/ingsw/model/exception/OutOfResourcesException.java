package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.enumeration.ErrorMessage;

public class OutOfResourcesException extends Exception{
    private final ErrorMessage errorMessage;

    public OutOfResourcesException() {
        errorMessage = ErrorMessage.OutOfResoucesError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
