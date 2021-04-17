package it.polimi.ingsw.model.exception;

import it.polimi.ingsw.model.enumeration.ErrorMessage;

public class ChoseResourcesException extends Exception{
    private final int OfYourChoiceInput;
    private final int OfYourChoiceOutput;
    private final ErrorMessage errorMessage;


    public ChoseResourcesException(int ofYourChoiceInput, int ofYourChoiceOutput) {
        OfYourChoiceInput = ofYourChoiceInput;
        OfYourChoiceOutput = ofYourChoiceOutput;
        errorMessage = ErrorMessage.ChooseResourceError;
    }

    public int getOfYourChoiceInput() {
        return OfYourChoiceInput;
    }

    public int getOfYourChoiceOutput() {
        return OfYourChoiceOutput;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
