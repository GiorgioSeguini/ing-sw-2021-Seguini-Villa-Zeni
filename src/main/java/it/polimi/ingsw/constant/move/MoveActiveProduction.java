package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.ProductionPower;

import java.util.ArrayList;

/**
 * The type Move active production. Active the selected production
 */
public class MoveActiveProduction extends MoveType {

    /**
     * The constant className.
     */
    public static final String className= "MoveActiveProduction";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    private ArrayList<ProductionPower> toActive;

    /**
     * Instantiates a new Move active production.
     *
     * @param idPlayer the id of the player
     */
    public MoveActiveProduction(int idPlayer) {
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
     * @see MoveType#simpleCheck(Game, PlayerStatus[]) called
     * @see MoveType#canPerform(Game)
     */
    @Override
    public boolean canPerform(Game game) {
        return super.simpleCheck(game, allowedStatus);
    }

    /**
     * Sets the list of the production of list player want to active.
     *
     * @param toActive of type Arraylist<ProductionPower> - a list containing the production ypu want to active
     */
    public void setToActive(ArrayList<ProductionPower> toActive) {
        this.toActive = toActive;
    }

    /**
     * Gets  the list of the production of list player want to active, default value is null
     *
     * @return toActive Arraylist<ProductionPower> - a list containing the production ypu want to active
     */
    public ArrayList<ProductionPower> getToActive() {
        return toActive;
    }
}
