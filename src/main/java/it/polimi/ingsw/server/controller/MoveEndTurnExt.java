package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;

public class MoveEndTurnExt extends MoveEndTurn implements Performable {

    public MoveEndTurnExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerformExt(GameExt game){
        Player player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }
        return true;
    }

    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);
        game.nextTurn();
    }

    /**
     *
     * @return of type String: the class name.
     */
    @Override
    public String getClassName() {
        return className;
    }
}
