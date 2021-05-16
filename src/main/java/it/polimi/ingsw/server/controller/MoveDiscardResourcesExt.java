package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveDiscardResources;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;


public class MoveDiscardResourcesExt extends MoveDiscardResources implements Performable {

    public MoveDiscardResourcesExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerform(GameExt game){
        return super.canPerform(game);
    }

    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        try {
            player.getConverter().setResources(player.getConverter().getResources().sub(getToDiscard()));
            player.getDepots().addResourcesFromMarket(player.getConverter().getResources());
            player.getConverter().CleanConverter();
            for (int i = 0; i < getToDiscard().size(); i++) {
                for (Player y : game.getPlayers()) {
                    if (!y.equals(player)) {
                        player.getFaithTrack().addPoint();
                    }
                }
                game.popesInspection();
            }
        } catch (OutOfResourcesException e) {
            player.setErrorMessage(e.getErrorMessage());
            return;
        }catch (UnableToFillException e){
            player.setErrorMessage(e.getErrorMessage());
            return;
        }


        player.setStatus(PlayerStatus.MovePerformed);
    }

    @Override
    public String getClassName() {
        return className;
    }
}
