package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;

/**
 * The type Move end turn. Move used to ends players' turn.
 * Mandatory at the end of the turn
 */
public class MoveEndTurn extends MoveType {

    /**
     * The constant className.
     */
    public static final String className= "MoveEndTurn";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.MovePerformed};

    /**
     * Instantiates a new Move end turn.
     *
     * @param idPlayer the id of the player
     */
    public MoveEndTurn(int idPlayer) {
        super(idPlayer);
    }

    /**
     * @see MoveType#getClassName() 
     */
    @Override
    public String getClassName() {
        return className;
    }

    /**
     * @see MoveType#simpleCheck(Game, PlayerStatus[]) called
     * @see MoveType#canPerform(Game) 
     */
    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }

}
