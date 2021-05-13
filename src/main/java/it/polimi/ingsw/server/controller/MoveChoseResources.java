package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.exception.ChoseResourcesException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

public class MoveChoseResources extends MoveType{
    private final NumberOfResources ofYourChoiceInput;
    private final NumberOfResources ofYourChoiceOutput;
    public static final String className= "MoveChoseResources";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToChoseRes};

    public MoveChoseResources(int idPlayer, NumberOfResources ofYourChoiceInput, NumberOfResources ofYourChoiceOutput) {
        super(idPlayer);
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
        return super.simpleCheck(game, allowedStatus);
    }

    @Override
    public void performMove(Game game) {
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

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

    @Override
    public String getClassName() {
        return className;
    }
}
