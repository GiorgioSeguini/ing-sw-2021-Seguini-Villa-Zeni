package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.enumeration.ErrorMessage;

public class NoMoreLeaderCardAliveException extends Exception{
    private final ErrorMessage errorMessage;

    public NoMoreLeaderCardAliveException() {
        errorMessage = ErrorMessage.NoMoreLeaderCardAliveError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
