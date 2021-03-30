package it.polimi.ingsw.model.exception;

public class HaveToChooseException extends Exception{
    public HaveToChooseException(){
        super("Player! Now you have to choose! I cannot handle it");
    }
}
