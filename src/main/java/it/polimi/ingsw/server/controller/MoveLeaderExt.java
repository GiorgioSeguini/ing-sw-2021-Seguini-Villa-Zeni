package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.move.MoveLeader;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.LeaderCardExt;
import it.polimi.ingsw.server.model.PlayerExt;


/**
 * MoveLeaderExt class.
 * Extends MoveLeader and implements Performable.
 * Manage the leader move.
 */
public class MoveLeaderExt extends MoveLeader implements Performable {

    /**
     * Instantiates a new Move leader ext.
     *
     * @param idPlayer of type int: the player's ID.
     */
    public MoveLeaderExt(int idPlayer) {
        super(idPlayer);
    }

    /**
     * Check if the player has the status to perform this move in this game (if the one hasn't, set the error message to MoveNotAllowed).
     * Check if the player really own the card that is going to play or discard and if the one doesn't own the card set the error message on CardNotOwned.
     * @param game of type GameExt: the game
     * @return True if the player can perform the move. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game){
        PlayerExt player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }

        LeaderCard leaderCard = game.findLeaderCard(getIdLeaderCard());
        if(leaderCard==null) return false;

        boolean isPresent = false;

        for (LeaderCard c : player.getPersonalBoard().getLeaderCards()) {
            if (c.getId() == leaderCard.getId()) {
                isPresent = true;
            }
        }
        if (!isPresent) {
            player.setErrorMessage(ErrorMessage.CardNotOwned);
            return false;
        }

        return true;
    }

    /**
     * Method that perform the move.
     * If there isn't any problem set the error message on NoError, else on BadChoice.
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        LeaderCardExt leaderCard = game.findLeaderCard(getIdLeaderCard());

        player.setErrorMessage(ErrorMessage.NoError);

        if (getMove() == 0) {
            if (!leaderCard.setPlayed(player)) {
                player.setErrorMessage(ErrorMessage.BadChoice);
                return;
            }
        }

        if (getMove() == 1)
            if (!leaderCard.setDiscard(player)) {
                player.setErrorMessage(ErrorMessage.BadChoice);
                //return;
            }
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
