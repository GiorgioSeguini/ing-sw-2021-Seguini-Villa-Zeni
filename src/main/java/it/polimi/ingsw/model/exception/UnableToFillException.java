package it.polimi.ingsw.model.exception;

public class UnableToFillException extends Exception{
    public UnableToFillException(){
        super("Attention! I'm not able to fill the deposit with this resources.");
    }
}
