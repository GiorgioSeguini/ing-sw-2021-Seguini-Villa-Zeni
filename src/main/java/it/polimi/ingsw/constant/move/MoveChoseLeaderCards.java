package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;

import java.util.ArrayList;


public class MoveChoseLeaderCards extends MoveType {

    private ArrayList<Integer> indexLeaderCards;
    public static final String className= "MoveChoseLeaderCards";

    public MoveChoseLeaderCards(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setIndexLeaderCards(ArrayList<Integer> indexLeaderCards) {
        this.indexLeaderCards = new ArrayList<>();
        this.indexLeaderCards.addAll(indexLeaderCards);
    }

    public ArrayList<Integer> getIndexLeaderCards() {
        return indexLeaderCards;
    }

    @Override
    public boolean canPerform(Game game) {
        if(!initialMove(game))
            return false;
        return !game.getPlayerFromID(getIdPlayer()).getPersonalBoard().isReady();
    }

}
