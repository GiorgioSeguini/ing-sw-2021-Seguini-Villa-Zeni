package it.polimi.ingsw.model.exception;

public class ChoseResourcesException extends Exception{
    private final int OfYourChoiceInput;
    private final int OfYourChoiceOutput;


    public ChoseResourcesException(int ofYourChoiceInput, int ofYourChoiceOutput) {
        OfYourChoiceInput = ofYourChoiceInput;
        OfYourChoiceOutput = ofYourChoiceOutput;
    }

    public int getOfYourChoiceInput() {
        return OfYourChoiceInput;
    }

    public int getOfYourChoiceOutput() {
        return OfYourChoiceOutput;
    }
}
