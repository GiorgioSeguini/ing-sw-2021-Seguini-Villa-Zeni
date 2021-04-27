package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.enumeration.ErrorMessage;
import it.polimi.ingsw.server.model.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;


public class MoveLeader extends MoveType{

    int move;
    LeaderCard leaderCard;

    public MoveLeader(Player player, int move, LeaderCard leaderCard) {
        super(player);
        this.move = move;
        this.leaderCard = leaderCard;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.Active, PlayerStatus.MovePerformed};
    }

    @Override
    public boolean canPerform(Game game){
        if(!super.canPerform(game)) return false;

        boolean isPresent = false;
        try {
            for (LeaderCard c : player.getPersonalBoard().getLeaderCards())
                if (c.equals(leaderCard)) {
                    isPresent = true;
                    leaderCard = c;
                }
        } catch (NoMoreLeaderCardAliveException e) {
            //Il player non ha più carte leader in mano
            player.setErrorMessage(e.getErrorMessage());
            return false;
        }

        if (!isPresent) {
            player.setErrorMessage(ErrorMessage.CardNotOwned);
            return false;
        }

        return true;
    }

    @Override
    public void performMove(Game game) {


        if (move == 0) {
            if (!leaderCard.setPlayed(player)) {
                player.setErrorMessage(ErrorMessage.BadChoice);
                return;
            }
        }

        if (move == 1)
            if (!leaderCard.setDiscard(player)) {
                player.setErrorMessage(ErrorMessage.BadChoice);
                //return;
            }
    }
}
