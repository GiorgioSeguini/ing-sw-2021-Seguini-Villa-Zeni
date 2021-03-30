package it.polimi.ingsw.model.exception;

public class UnableToFillError extends Exception{
    public UnableToFillError(){
        super("Attention! I'm not able to fill the deposit with this resources.");
    }
}
