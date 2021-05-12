package it.polimi.ingsw.client.move;

public class MoveLeader extends MoveType {

    int move;
    int idLeaderCard;
    public static final String className= "MoveLeader";

    public MoveLeader(int idPlayer, int move, int idLeaderCard) {
        super(idPlayer);
        this.move = move;
        this.idLeaderCard = idLeaderCard;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
