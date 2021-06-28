package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;

/**
 * MoveEndTurnExt class.
 * Extends MoveEndTurn and implements Performable interface.
 * Manage the turn's end.
 */
public class MoveEndTurnExt extends MoveEndTurn implements Performable {

    /**
     * Instantiates a new Move end turn ext.
     *
     * @param idPlayer of type int: the player's ID.
     */
    public MoveEndTurnExt(int idPlayer) {
        super(idPlayer);
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
     * Method that perform the move and set the error message on NoError and passes the turn to another player.
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);
        game.nextTurn();
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
