package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

public class HaveToChooseException extends Exception{
    private final ErrorMessage errorMessage;

    public HaveToChooseException() {
        errorMessage = ErrorMessage.HaveToChooseError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
