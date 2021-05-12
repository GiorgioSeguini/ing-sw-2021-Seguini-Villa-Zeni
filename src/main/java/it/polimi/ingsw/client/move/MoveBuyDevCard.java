package it.polimi.ingsw.client.move;

public class MoveBuyDevCard extends MoveType {

    private final int pos;
    private final int indexCardToBuy;
    public static final String className= "MoveBuyDevCard";

    public MoveBuyDevCard(int idPlayer, int pos, int indexCardToBuy) {
        super(idPlayer);
        this.pos = pos;
        this.indexCardToBuy = indexCardToBuy;
    }

    @Override
    public String getClassName() {
        return className;
    }

}
