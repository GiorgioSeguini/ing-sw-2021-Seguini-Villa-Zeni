package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
import it.polimi.ingsw.model.enumeration.PlayerStatus;

public class MoveEndTurn extends MoveType{


    public MoveEndTurn(Player player) {
        super(player);
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.MovePerformed};
    }

    @Override
    public boolean canPerform(Game game){
        return super.canPerform(game);
    }

    @Override
    public void performMove(Game game) {
        game.nextTurn();
    }

}
