package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.GameStatus;

import java.util.ArrayList;


public class MoveChoseLeaderCards extends MoveType{

    private final ArrayList<Integer> indexLeaderCards;

    public MoveChoseLeaderCards(Player player, ArrayList<Integer> indexLeaderCards) {
        super(player);
        this.indexLeaderCards = new ArrayList<>();
        this.indexLeaderCards.addAll(indexLeaderCards);
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

        ArrayList<LeaderCard> leaderCards = game.findMoreLeaderCard(indexLeaderCards);

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
        ArrayList<LeaderCard> leaderCards = game.findMoreLeaderCard(indexLeaderCards);
        try {
            player.getPersonalBoard().addLeaderCard(leaderCards.toArray(new LeaderCard[0]));
        }catch (IndexOutOfBoundsException e){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return;
        }

        game.updateStatus();
    }

}
