package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveChoseInitialResources;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;

import it.polimi.ingsw.server.model.DepotsExt;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.exception.UnableToFillException;

public class MoveChoseInitialResourcesExt extends MoveChoseInitialResources implements Performable {


    public MoveChoseInitialResourcesExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerform(GameExt game) {
        return super.canPerform(game);
    }

    @Override
    public void performMove(GameExt game){
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        try{
            ((DepotsExt)player.getDepots()).addResourcesFromMarket(getResources());
        }catch(UnableToFillException ignored){}

        game.updateStatus();
    }

    @Override
    public String getClassName() {
        return className;
    }
}
