package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.enumeration.ErrorMessage;

public class FinalTurnException extends Exception{

    private final boolean lorenzoWin;
    private final ErrorMessage errorMessage;

    public FinalTurnException(){
        lorenzoWin = false;
        errorMessage = ErrorMessage.FinalTurnError;
    }

    FinalTurnException(boolean lorenzoWin){
        this.lorenzoWin = lorenzoWin;
        errorMessage = ErrorMessage.FinalTurnError;
    }

    public boolean isLorenzoWin() {
        return lorenzoWin;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
