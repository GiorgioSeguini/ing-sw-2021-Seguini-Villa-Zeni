package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.GameStatus;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;

public class MoveChoseLeaderCards extends MoveType{

    private final LeaderCard[] leaderCards;

    public MoveChoseLeaderCards(Player player, LeaderCard[] leaderCards) {
        super(player);
        this.leaderCards = leaderCards;
    }

    @Override
    public boolean performMove(Game game){
        if(game.getStatus() != GameStatus.Initial){
            //TODO errror message
            return false;
        }

        if(game.getPlayerIndex(player)<0){
            //TODO error message
            return false;
        }

        boolean goodChoice = true;
        for(LeaderCard card : leaderCards){
            if(game.getActivableLeadCard(player).contains(card)){
                goodChoice = false;
            }
        }
        if(!goodChoice){
            //TODO error message
            return false;
        }

        try {
            player.getPersonalBoard().addLeaderCard(leaderCards);
        }catch (IndexOutOfBoundsException e){
            //TODO error message
            return false;
        }

        game.updateStatus();
        return true;
    }

}
