package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.NumberOfResources;

public class MoveChoseResources extends MoveType {
    private final NumberOfResources ofYourChoiceInput;
    private final NumberOfResources ofYourChoiceOutput;
    public static final String className= "MoveChoseResources";

    public MoveChoseResources(int idPlayer, NumberOfResources ofYourChoiceInput, NumberOfResources ofYourChoiceOutput) {
        super(idPlayer,className);
        this.ofYourChoiceInput = ofYourChoiceInput;
        this.ofYourChoiceOutput = ofYourChoiceOutput;
    }

}
