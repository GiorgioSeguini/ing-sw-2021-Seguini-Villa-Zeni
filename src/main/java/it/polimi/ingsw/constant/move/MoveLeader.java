package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;

/**
 * The type Move leader. Move to active or discard a leader Card
 */
public class MoveLeader extends MoveType {

    /**
     * The constant className.
     */
    public static final String className= "MoveLeader";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active, PlayerStatus.MovePerformed};

    private int move;
    private int idLeaderCard;

    /**
     * Instantiates a new Move leader.
     *
     * @param idPlayer the id of the player
     */
    public MoveLeader(int idPlayer) {
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
     * Sets move. 0 to play the leader card, 1 to discard the leader card. Other value can be setted but will be rejected by the server
     *
     * @param move of type int - the move
     */
    public void setMove(int move) {
        this.move = move;
    }

    /**
     * Sets id of the interested leader card
     *
     * @param idLeaderCard the id leader card of type int
     */
    public void setIdLeaderCard(int idLeaderCard) {
        this.idLeaderCard = idLeaderCard;
    }

    /**
     * Gets move. 0 to play the leader card, 1 to discard the leader card. Other value are possible but will be rejected by the server
     *
     * @return the move
     */
    public int getMove() {
        return move;
    }

    /**
     * Gets id of the interested leader card
     *
     * @return the id leader card of type int
     */
    public int getIdLeaderCard() {
        return idLeaderCard;
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
