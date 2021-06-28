package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.LeaderCardExt;
import it.polimi.ingsw.server.model.PlayerExt;

import java.util.ArrayList;


public class MoveChoseLeaderCardsExt extends MoveChoseLeaderCards implements Performable {


    public MoveChoseLeaderCardsExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerformExt(GameExt game){
        Player player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
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
