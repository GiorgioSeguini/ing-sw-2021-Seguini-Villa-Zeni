package it.polimi.ingsw.model;

public class UnableToFillError extends Exception{
    UnableToFillError(){
        super("Attention! I'm not able to fill the deposit with this resources.");
    }
}
