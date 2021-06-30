package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.LeaderCardExt;
import it.polimi.ingsw.server.model.PlayerExt;

import java.util.ArrayList;


/**
 * MoveChoseLeaderCardsExt class.
 * Extends MoveChoseLeaderCards and implements Performable interface.
 * Manage the chose leader card move.
 */
public class MoveChoseLeaderCardsExt extends MoveChoseLeaderCards implements Performable {


    /**
     * Instantiates a new Move chose leader cards ext.
     *
     * @param idPlayer of type int: the player's ID.
     */
    public MoveChoseLeaderCardsExt(int idPlayer) {
        super(idPlayer);
    }

    /**
     * Check if the player has the status to perform this move in this game (if the one hasn't, set the error message to MoveNotAllowed. Can also set the error message to CardNotOwned).
     * @param game of type GameExt: the game
     * @return True if the player can perform the move. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game){
        Player player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }

        if(getIndexLeaderCards()==null){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }

        ArrayList<LeaderCardExt> leaderCards = game.findMoreLeaderCard(getIndexLeaderCards());
        boolean goodChoice = true;
        for(LeaderCard card : leaderCards){
            if(!game.getActivableLeadCard(player).contains(card)){
                goodChoice = false;
            }
        }
        if(!goodChoice){
            player.setErrorMessage(ErrorMessage.CardNotOwned);
            return false;
        }

        return true;

    }

    /**
     * Method that perform the move and update the game status.
     * Set the error message too (NoError or BadChoice).
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game){
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        ArrayList<LeaderCardExt> leaderCards = game.findMoreLeaderCard(getIndexLeaderCards());
        try {
            player.getPersonalBoard().addLeaderCard(leaderCards.toArray(new LeaderCardExt[2]));
        }catch (IllegalArgumentException e){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return;
        }

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
