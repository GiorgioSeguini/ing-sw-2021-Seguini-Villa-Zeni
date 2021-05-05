package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class MoveEndTurn extends MoveType{

    public static final String className= "MoveEndTurn";

    public MoveEndTurn(Player player) {
        super(player, className);
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
