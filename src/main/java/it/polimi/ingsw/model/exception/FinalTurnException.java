package it.polimi.ingsw.model.exception;

public class FinalTurnException extends Exception{

    private final boolean lorenzoWin;

    public FinalTurnException(){
        lorenzoWin = false;
    }

    FinalTurnException(boolean lorenzoWin){
        this.lorenzoWin = lorenzoWin;
    }

    public boolean isLorenzoWin() {
        return lorenzoWin;
    }
}
