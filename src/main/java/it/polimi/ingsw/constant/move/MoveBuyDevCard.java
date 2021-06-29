package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;

/**
 * The type Move buy dev card. Buy a card from the DashBoard
 */
public class MoveBuyDevCard extends MoveType {

    /**
     * The constant className.
     */
    public static final String className= "MoveBuyDevCard";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    private int pos;
    private int indexCardToBuy;
    /**
     * Instantiates a new Move buy dev card.
     *
     * @param idPlayer the id of the player
     */
    public MoveBuyDevCard(int idPlayer) {
        super(idPlayer);
    }

    /**
     *
     * @see MoveType#getClassName()
     */
    @Override
    public String getClassName() {
        return className;
    }

    /**
     * Gets the pos in the Personal Board where your want to put your card once bought
     *
     * @return the pos to place the card
     */
    public int getPos() {
        return pos;
    }

    /**
     * Gets the id of the card you want to buy
     *
     * @return the id of card to buy
     */
    public int getIndexCardToBuy() {
        return indexCardToBuy;
    }

    /**
     * Sets the pos in the Personal Board where your want to put your card once bought
     *
     * @param pos the pos to place the card
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * Sets the id of the card you want to buy
     *
     * @param indexCardToBuy the id of card to buy
     */
    public void setIndexCardToBuy(int indexCardToBuy) {
        this.indexCardToBuy = indexCardToBuy;
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
