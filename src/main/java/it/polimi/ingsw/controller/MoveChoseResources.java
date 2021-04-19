package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.exception.ChoseResourcesException;
import it.polimi.ingsw.model.exception.OutOfResourcesException;

public class MoveChoseResources extends MoveType{
     private final NumberOfResources ofYourChoiceInput;
    private final NumberOfResources ofYourChoiceOutput;

    public MoveChoseResources(Player active, NumberOfResources ofYourChoiceInput, NumberOfResources ofYourChoiceOutput) {
        super(active);
        this.ofYourChoiceInput = ofYourChoiceInput;
        this.ofYourChoiceOutput = ofYourChoiceOutput;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToChoseRes};
    }

    public NumberOfResources getOfYourChoiceInput() {
        return ofYourChoiceInput;
    }

    public NumberOfResources getOfYourChoiceOutput() {
        return ofYourChoiceOutput;
    }

    @Override
    public boolean canPerform(Game game){
        return super.canPerform(game);
    }

    @Override
    public void performMove(Game game) {

        try {
            player.getToActive().active(player, ofYourChoiceInput, ofYourChoiceOutput);
        }catch(ChoseResourcesException e){
            player.setErrorMessage(e.getErrorMessage());
            return;
        }catch(OutOfResourcesException e){
            player.setErrorMessage(e.getErrorMessage());
            return;
        }
        game.popesInspection();

        player.setStatus(PlayerStatus.MovePerformed);
    }
}
