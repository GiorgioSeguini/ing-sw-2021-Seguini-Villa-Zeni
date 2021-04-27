package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.enumeration.ErrorMessage;

public class UnableToFillException extends Exception{
    private final ErrorMessage errorMessage;

    public UnableToFillException(){
        errorMessage = ErrorMessage.UnableToFillError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
