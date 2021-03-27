package it.polimi.ingsw.model;

public class HaveToChooseException extends Exception{
    HaveToChooseException(){
        super("Player! Now you have to choose! I cannot handle it");
    }
}
