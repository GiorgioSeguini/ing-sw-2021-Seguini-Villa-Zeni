package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.exception.ChoseResourcesException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

/**
 * MoveChoseResourcesExt class.
 * Extends MoveChoseResources and implements Performable.
 * Manage the chose resources move.
 */
public class MoveChoseResourcesExt extends MoveChoseResources implements Performable {

    /**
     * Instantiates a new Move chose resources ext.
     *
     * @param idPlayer of type int: the player's ID.
     * @param ofYourChoiceInput of type NumberOfResources: resources of your choice input
     * @param ofYourChoiceOutput of type NumberOfResources: resources of your choice output
     */
    public MoveChoseResourcesExt(int idPlayer, NumberOfResources ofYourChoiceInput, NumberOfResources ofYourChoiceOutput) {
        super(idPlayer);
        this.setOfYourChoiceInput(ofYourChoiceInput);
        this.setOfYourChoiceOutput(ofYourChoiceOutput);
    }


    /**
     * Check if the player has the status to perform this move in this game (if the one hasn't, set the error message to MoveNotAllowed).
     * @param game of type GameExt: the game
     * @return True if the player has the status to perform this move in this game. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game){
        Player player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }
        return true;
    }

    /**
     * Method that perform the move and set the player status on MovePerformed if it's all fine.
     * Set the error message too (NoError or an error from ChoseResourcesException or OutOfResourcesException).
     * @param game of type GameExt: the game
     */
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

    /**
     *
     * @return of type String: the class name.
     */
    @Override
    public String getClassName() {
        return className;
    }
}
