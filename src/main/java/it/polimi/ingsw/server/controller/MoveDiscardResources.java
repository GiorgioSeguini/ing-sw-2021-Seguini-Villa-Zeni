package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;


public class MoveDiscardResources extends MoveType{
    private final NumberOfResources toDiscard;
    public static final String className= "MoveDiscardResources";

    public MoveDiscardResources(int idPlayer, NumberOfResources toDiscard){
        super(idPlayer,className);
        this.toDiscard=toDiscard;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToStore};
    }

    public NumberOfResources getToDiscard() {
        return toDiscard;
    }

    @Override
    public boolean canPerform(Game game){
        return super.canPerform(game);
    }

    @Override
    public void performMove(Game game) {
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        try {
            player.getConverter().setResources(player.getConverter().getResources().sub(toDiscard));
            player.getDepots().addResourcesFromMarket(player.getConverter().getResources());
            player.getConverter().CleanConverter();
            for (int i = 0; i < toDiscard.size(); i++) {
                for (Player y : game.getPlayers()) {
                    if (y != player) {
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
}
