package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class MoveDiscardResources extends MoveType {
    private NumberOfResources toDiscard;
    public static final String className= "MoveDiscardResources";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToStore};

    public MoveDiscardResources(int idPlayer){
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public NumberOfResources getToDiscard() {
        return toDiscard;
    }

    public void setToDiscard(NumberOfResources toDiscard) {
        this.toDiscard = toDiscard;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}
