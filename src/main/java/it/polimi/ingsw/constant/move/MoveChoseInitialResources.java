package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;

public class MoveChoseInitialResources extends MoveType {

    private NumberOfResources resources;
    public static final String className= "MoveChoseInitialResources";

    public MoveChoseInitialResources(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setResources(NumberOfResources resources) {
        this.resources = resources;
    }

    public NumberOfResources getResources() {
        return resources;
    }

    @Override
    public boolean canPerform(Game game) {
        if(!initialMove(game))
            return false;
        return game.getInitialResources(getIdPlayer())>game.getPlayerFromID(getIdPlayer()).getDepots().getResources().size();
    }

}
