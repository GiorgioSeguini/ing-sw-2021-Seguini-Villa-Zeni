package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

public class NoMoreLeaderCardAliveException extends Exception{
    private final ErrorMessage errorMessage;

    public NoMoreLeaderCardAliveException() {
        errorMessage = ErrorMessage.NoMoreLeaderCardAliveError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
