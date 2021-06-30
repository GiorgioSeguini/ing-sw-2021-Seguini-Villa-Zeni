package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveChoseInitialResources;
import it.polimi.ingsw.server.model.DepotsExt;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.exception.UnableToFillException;

/**
 * MoveChoseInitialResourcesExt class.
 * Extends MoveChoseInitialResources and implements Performable interface.
 * Manage the chose initial resources move.
 */
public class MoveChoseInitialResourcesExt extends MoveChoseInitialResources implements Performable {


    /**
     * Instantiates a new Move chose initial resources ext.
     *
     * @param idPlayer of type int: the player's ID.
     */
    public MoveChoseInitialResourcesExt(int idPlayer) {
        super(idPlayer);
    }

    /**
     * Checks if the player has the status to perform this move in this game (if the one hasn't, sets the error message to MoveNotAllowed).
     * @param game of type GameExt: the game
     * @return True if the player has the status to perform this move in this game and if the player's initial resources are equals to the move's one.
     *         Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game) {
        Player player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }
        return game.getInitialResources(getIdPlayer()) == getResources().size();
    }

    /**
     * Method that performs the move and updates the game status.
     * Sets the error message too (NoError).
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game){
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        try{
            ((DepotsExt)player.getDepots()).addResourcesFromMarket(getResources());
        }catch(UnableToFillException ignored){}

        game.updateStatus();
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
