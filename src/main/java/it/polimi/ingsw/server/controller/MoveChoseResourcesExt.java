package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.exception.ChoseResourcesException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

public class MoveChoseResourcesExt extends MoveChoseResources implements Performable {

    public MoveChoseResourcesExt(int idPlayer, NumberOfResources ofYourChoiceInput, NumberOfResources ofYourChoiceOutput) {
        super(idPlayer);
        this.setOfYourChoiceInput(ofYourChoiceInput);
        this.setOfYourChoiceOutput(ofYourChoiceOutput);
    }


    @Override
    public boolean canPerformExt(GameExt game){
        return super.canPerform(game);
    }

    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        try {
            player.getToActive().active(player, this.getOfYourChoiceInput(), this.getOfYourChoiceOutput());
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
