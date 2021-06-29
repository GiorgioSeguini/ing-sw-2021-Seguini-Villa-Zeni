package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;

/**
 * The type Move chose initial resources. Chose the initial resources if player has the rights to do it
 */
public class MoveChoseInitialResources extends MoveType {


    /**
     * The constant className.
     */
    public static final String className= "MoveChoseInitialResources";
    private NumberOfResources resources;
    /**
     * Instantiates a new Move chose initial resources.
     *
     * @param idPlayer the id of the player
     */
    public MoveChoseInitialResources(int idPlayer) {
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
     * Sets the initial resources player want
     *
     * @param resources the resources
     */
    public void setResources(NumberOfResources resources) {
        this.resources = resources;
    }

    /**
     * Gets the initial resources player want
     *
     * @return the resources
     */
    public NumberOfResources getResources() {
        return resources;
    }

    /**
     * @see MoveType#initialMove(Game) called
     * @see MoveType#canPerform(Game)
     */
    @Override
    public boolean canPerform(Game game) {
        if(!initialMove(game))
            return false;
        return game.getInitialResources(getIdPlayer())>game.getPlayerFromID(getIdPlayer()).getDepots().getResources().size();
    }

}
