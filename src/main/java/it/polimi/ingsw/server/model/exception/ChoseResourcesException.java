package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;

/**
 * The type Chose resources exception.
 */
public class ChoseResourcesException extends Exception{
    private final int OfYourChoiceInput;
    private final int OfYourChoiceOutput;
    private final ErrorMessage errorMessage;


    /**
     * Instantiates a new Chose resources exception.
     *
     * @param ofYourChoiceInput of type int: resources of your choice input
     * @param ofYourChoiceOutput of type int: resources of your choice output
     */
    public ChoseResourcesException(int ofYourChoiceInput, int ofYourChoiceOutput) {
        OfYourChoiceInput = ofYourChoiceInput;
        OfYourChoiceOutput = ofYourChoiceOutput;
        errorMessage = ErrorMessage.ChooseResourceError;
    }

    /**
     * Gets the resources of your choice input.
     *
     * @return of type int: resources of your choice input
     */
    public int getOfYourChoiceInput() {
        return OfYourChoiceInput;
    }

    /**
     * Gets the resources of your choice output.
     *
     * @return of type int: resources of your choice output
     */
    public int getOfYourChoiceOutput() {
        return OfYourChoiceOutput;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
