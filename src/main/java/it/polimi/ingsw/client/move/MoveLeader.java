package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class MoveLeader extends MoveType {

    private int move;
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

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}
