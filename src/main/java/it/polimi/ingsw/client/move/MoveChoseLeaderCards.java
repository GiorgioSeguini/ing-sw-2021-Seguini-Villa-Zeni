package it.polimi.ingsw.client.move;

import java.util.ArrayList;


public class MoveChoseLeaderCards extends MoveType {

    private final ArrayList<Integer> indexLeaderCards;
    public static final String className= "MoveChoseLeaderCards";

    public MoveChoseLeaderCards(int idPlayer, ArrayList<Integer> indexLeaderCards) {
        super(idPlayer, className);
        this.indexLeaderCards = new ArrayList<>();
        this.indexLeaderCards.addAll(indexLeaderCards);
    }

}
