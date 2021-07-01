package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveDiscardResources;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;


/**
 * MoveDiscardResourcesExt class.
 * Extends MoveDiscardResources and implements Performable.
 * Manage the discard resources move.
 */
public class MoveDiscardResourcesExt extends MoveDiscardResources implements Performable {

    /**
     * Instantiates a new Move discard resources ext.
     *
     * @param idPlayer the id player
     */
    public MoveDiscardResourcesExt(int idPlayer) {
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
     * Method that perform the move and set the player status on MovePerformed if it's all fine.
     * Discard waiting resources in the converter.
     * Can set the error message on an error from the OutOfResourcesException or from the UnableToFillException.
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        try {
            player.getConverter().setResources(player.getConverter().getResources().sub(getToDiscard()));
            for (int i = 0; i < getToDiscard().size(); i++) {
                for (Player y : game.getPlayers()) {
                    if (!y.equals(player)) {
                        ((PlayerExt)y).getFaithTrack().addPoint();
                    }
                }
                if(game.isSinglePlayer()) {
                    game.getSoloGame().getFaithTrack().addPoint();
                }
                game.popesInspection();
            }
            player.getDepots().addResourcesFromMarket(player.getConverter().getResources());
            player.getConverter().CleanConverter();
        } catch (OutOfResourcesException e) {
            player.setErrorMessage(e.getErrorMessage());
            return;
        }catch (UnableToFillException e){
            player.setErrorMessage(e.getErrorMessage());
            return;
        }


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
