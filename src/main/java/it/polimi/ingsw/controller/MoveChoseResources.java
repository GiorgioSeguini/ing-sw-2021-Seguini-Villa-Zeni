package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
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
    }

    public NumberOfResources getOfYourChoiceInput() {
        return ofYourChoiceInput;
    }

    public NumberOfResources getOfYourChoiceOutput() {
        return ofYourChoiceOutput;
    }

    @Override
    public boolean canPerform(Game game){
        if (!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player = game.getCurrPlayer();
        if (player.getStatus() != PlayerStatus.NeedToChoseRes) {
            //TODO error Message
            return false;
        }

        return true;
    }

    @Override
    public void performMove(Game game) {

        try {
            player.getToActive().active(player, ofYourChoiceInput, ofYourChoiceOutput);
        }catch(ChoseResourcesException e){
            //TODO error message
            return;
        }catch(OutOfResourcesException e){
            //TODO something different
            return;
        }
        game.popesInspection();

        player.setStatus(PlayerStatus.MovePerformed);
        return;
    }
}
