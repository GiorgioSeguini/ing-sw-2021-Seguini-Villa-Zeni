package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.NumberOfResources;

public class MoveChoseResources extends MoveType {
    private NumberOfResources ofYourChoiceInput;
    private NumberOfResources ofYourChoiceOutput;
    public static final String className= "MoveChoseResources";

    public MoveChoseResources(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setOfYourChoiceInput(NumberOfResources ofYourChoiceInput) {
        this.ofYourChoiceInput = ofYourChoiceInput;
    }

    public void setOfYourChoiceOutput(NumberOfResources ofYourChoiceOutput) {
        this.ofYourChoiceOutput = ofYourChoiceOutput;
    }

    @Override
    public boolean canPerform(Game game) {
        return initialMove(game);
    }
}
