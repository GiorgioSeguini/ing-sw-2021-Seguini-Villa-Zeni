package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;

import java.util.ArrayList;


/**
 * The type Move chose leader cards. Chose players initial LeaderCards
 */
public class MoveChoseLeaderCards extends MoveType {

    private ArrayList<Integer> indexLeaderCards;
    /**
     * The constant className.
     */
    public static final String className= "MoveChoseLeaderCards";

    /**
     * Instantiates a new Move chose leader cards.
     *
     * @param idPlayer the player's id
     */
    public MoveChoseLeaderCards(int idPlayer) {
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
     * Sets the ids of the selected leaderCards.
     *
     * @param indexLeaderCards of type ArrayList<Integer> - the list of ids of the selected leader cards
     */
    public void setIndexLeaderCards(ArrayList<Integer> indexLeaderCards) {
        this.indexLeaderCards = new ArrayList<>();
        this.indexLeaderCards.addAll(indexLeaderCards);
    }

    /**
     * Gets s the ids of the selected leaderCards.
     *
     * @return type ArrayList<Integer> - the list of ids of the selected leader cards
     */
    public ArrayList<Integer> getIndexLeaderCards() {
        return indexLeaderCards;
    }

    /**
     * @see MoveType#initialMove(Game) called
     * @see MoveType#canPerform(Game)
     */
    @Override
    public boolean canPerform(Game game) {
        if(!initialMove(game))
            return false;
        return !game.getPlayerFromID(getIdPlayer()).getPersonalBoard().isReady();
    }

}
