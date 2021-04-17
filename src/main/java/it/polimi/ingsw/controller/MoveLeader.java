package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;

import javax.swing.text.View;

public class MoveLeader extends MoveType{

    int move;
    LeaderCard leaderCard;

    public MoveLeader(Player player, View view, boolean isLastMove, int move, LeaderCard leaderCard) {
        super(player);
        this.move = move;
        this.leaderCard = leaderCard;
    }

    @Override
    public boolean canPerform(Game game){
        if (!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player = game.getCurrPlayer();
        if (player.getStatus() != PlayerStatus.Active && player.getStatus() != PlayerStatus.MovePerformed) {
            //TODO error Message
            return false;
        }

        boolean isPresent = false;
        try {
            for (LeaderCard c : player.getPersonalBoard().getLeaderCards())
                if (c.equals(leaderCard)) {
                    isPresent = true;
                    leaderCard = c;
                }
        } catch (NoMoreLeaderCardAliveException e) {
            //Il player non ha più carte leader in mano
            //TODO ERROR MESSAGE
            return false;
        }

        if (!isPresent) {
            //TODO ERROR MESSAGE
            return false;
        }

        return true;
    }

    @Override
    public void performMove(Game game) {


        if (move == 0) {
            if (!leaderCard.setPlayed(player)) {
                //TODO ERROR MESSAGE
                return;
            }
        }

        if (move == 1)
            if (!leaderCard.setDiscard(player)) {
                //TODO ERROR MESSAGE
                return;
            }
    }
}
