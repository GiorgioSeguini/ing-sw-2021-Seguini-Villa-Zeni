package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PersonalBoardExt;

import java.util.ArrayList;


public class MoveChoseLeaderCardsExt extends MoveChoseLeaderCards implements Performable {


    public MoveChoseLeaderCardsExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerform(Game game){
        if(!super.canPerform(game)) return false;

        Player player = game.getPlayerFromID(getIdPlayer());

        ArrayList<LeaderCard> leaderCards = ((GameExt)game).findMoreLeaderCard(getIndexLeaderCards());
        boolean goodChoice = true;
        for(LeaderCard card : leaderCards){
            if(!((GameExt)game).getActivableLeadCard(player).contains(card)){
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
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        ArrayList<LeaderCard> leaderCards = ((GameExt)game).findMoreLeaderCard(getIndexLeaderCards());
        try {
            ((PersonalBoardExt)player.getPersonalBoard()).addLeaderCard(leaderCards.toArray(new LeaderCard[0]));
        }catch (IndexOutOfBoundsException e){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return;
        }

        ((GameExt)game).updateStatus();
    }

    @Override
    public String getClassName() {
        return className;
    }

}
