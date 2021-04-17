package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.PlayerStatus;

public class MoveEndTurn extends MoveType{


    public MoveEndTurn(Player player) {
        super(player);
    }

    @Override
    public boolean canPerform(Game game){
        if (!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player = game.getCurrPlayer();
        if (player.getStatus() != PlayerStatus.MovePerformed) {
            //TODO error Message
            return false;
        }

        return true;
    }

    @Override
    public void performMove(Game game) {
        game.nextTurn();
    }

}
