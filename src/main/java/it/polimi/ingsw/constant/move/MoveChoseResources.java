package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class MoveChoseResources extends MoveType {
    private NumberOfResources ofYourChoiceInput;
    private NumberOfResources ofYourChoiceOutput;
    public static final String className= "MoveChoseResources";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToChoseRes};

    public MoveChoseResources(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public NumberOfResources getOfYourChoiceInput() {
        return ofYourChoiceInput;
    }

    public NumberOfResources getOfYourChoiceOutput() {
        return ofYourChoiceOutput;
    }

    public void setOfYourChoiceInput(NumberOfResources ofYourChoiceInput) {
        this.ofYourChoiceInput = ofYourChoiceInput;
    }

    public void setOfYourChoiceOutput(NumberOfResources ofYourChoiceOutput) {
        this.ofYourChoiceOutput = ofYourChoiceOutput;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}