package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.enumeration.ErrorMessage;
import it.polimi.ingsw.server.model.enumeration.GameStatus;

import java.util.ArrayList;


public class MoveChoseLeaderCards extends MoveType{

    private final ArrayList<LeaderCard> leaderCards;

    public MoveChoseLeaderCards(Player player, ArrayList<LeaderCard> leaderCards) {
        super(player);
        this.leaderCards = leaderCards;
    }

    @Override
    public boolean canPerform(Game game){
        if(game.getStatus() != GameStatus.Initial){
            player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }

        if(game.getPlayerIndex(player)<0){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }

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
    public void performMove(Game game){

        try {
            player.getPersonalBoard().addLeaderCard(leaderCards.toArray(new LeaderCard[0]));
        }catch (IndexOutOfBoundsException e){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return;
        }

        game.updateStatus();
    }

}
