package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;

/**
 * The type Move type market. Move to buy omething from the market
 */
public class MoveTypeMarket extends MoveType {

    private int indexToBuy; //from 0 to 6- from 0 to 3 for columns - from 4 to 6 for rows
    /**
     * The constant className.
     */
    public static final String className = "MovetypeMarket";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    /**
     * Instantiates a new Move type market.
     *
     * @param idPlayer the id player
     */
    public MoveTypeMarket(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    /**
     * Gets index to buy. it goes from 0 to 6
     * from 0 to 3 for columns
     * from 4 to 6 for rows
     *other values are possible but will be rejected by the server
     * 
     * @return the index to buy - int
     */
    public int getIndexToBuy() {
        return indexToBuy;
    }

    /**
     * Gets index to buy. it goes from 0 to 6
     * from 0 to 3 for columns
     * from 4 to 6 for rows
     * other values are possible but will be rejected by the server
     *
     * @param indexToBuy the index to buy - int
     */
    public void setIndexToBuy(int indexToBuy) {
        this.indexToBuy = indexToBuy;
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


