package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class MoveBuyDevCard extends MoveType {

    private int pos;
    private int indexCardToBuy;
    public static final String className= "MoveBuyDevCard";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    public MoveBuyDevCard(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public int getPos() {
        return pos;
    }

    public int getIndexCardToBuy() {
        return indexCardToBuy;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setIndexCardToBuy(int indexCardToBuy) {
        this.indexCardToBuy = indexCardToBuy;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}
