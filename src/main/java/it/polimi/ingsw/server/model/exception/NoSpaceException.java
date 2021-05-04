package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

public class NoSpaceException extends Exception{
    private final ErrorMessage errorMessage;

    public NoSpaceException() {
        errorMessage = ErrorMessage.NoSpaceError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
