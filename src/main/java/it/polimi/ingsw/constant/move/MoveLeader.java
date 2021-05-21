package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class MoveLeader extends MoveType {

    private int move; // TODO: 5/21/21 enum? actually 0 playability, 1 discard leader card 
    private int idLeaderCard;
    public static final String className= "MoveLeader";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active, PlayerStatus.MovePerformed};

    public MoveLeader(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void setIdLeaderCard(int idLeaderCard) {
        this.idLeaderCard = idLeaderCard;
    }

    public int getMove() {
        return move;
    }

    public int getIdLeaderCard() {
        return idLeaderCard;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}
