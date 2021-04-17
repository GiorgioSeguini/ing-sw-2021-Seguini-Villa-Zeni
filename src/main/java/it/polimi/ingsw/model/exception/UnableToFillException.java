package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.enumeration.ErrorMessage;

public class UnableToFillException extends Exception{
    private final ErrorMessage errorMessage;

    public UnableToFillException(){
        //super("Attention! I'm not able to fill the deposit with this resources.");
        errorMessage = ErrorMessage.UnableToFillError;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
