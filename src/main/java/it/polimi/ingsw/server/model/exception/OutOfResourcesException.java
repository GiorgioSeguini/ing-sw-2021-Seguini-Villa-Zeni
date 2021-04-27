package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.enumeration.ErrorMessage;

public class OutOfResourcesException extends Exception{
    private final ErrorMessage errorMessage;

    public OutOfResourcesException() {
        errorMessage = ErrorMessage.OutOfResourcesError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
