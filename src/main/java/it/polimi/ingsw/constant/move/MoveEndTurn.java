package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;

public class MoveEndTurn extends MoveType {

    public static final String className= "MoveEndTurn";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.MovePerformed};

    public MoveEndTurn(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }

}
