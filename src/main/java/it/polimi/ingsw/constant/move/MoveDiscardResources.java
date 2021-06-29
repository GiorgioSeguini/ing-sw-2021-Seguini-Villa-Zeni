package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;

/**
 * The type Move discard resources after they have been bought at the market.
 * After player buy something at the market she must declare if she wants to discard some of the new Resources or she keeps everything(if possible)
 * This move type is mandatory after player buy something at the market
 */
public class MoveDiscardResources extends MoveType {
    /**
     * The constant className.
     */
    public static final String className= "MoveDiscardResources";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToStore};
    private NumberOfResources toDiscard;

    /**
     * Instantiates a new Move discard resources.
     *
     * @param idPlayer the id player
     */
    public MoveDiscardResources(int idPlayer){
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
     * Gets the resources player wants to discard,default value is null.
     *
     * @return NumberOfResources - to discard
     */
    public NumberOfResources getToDiscard() {
        return toDiscard;
    }

    /**
     * Sets the resources player wants to discard, may be also an empty NumberOfResources
     *
     * @param toDiscard of type NumberOfResource - the resources player wants to discard
     */
    public void setToDiscard(NumberOfResources toDiscard) {
        this.toDiscard = toDiscard;
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
