package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillException;


public class MoveDiscardResources extends MoveType{
    private final NumberOfResources toDiscard;

    public MoveDiscardResources(Player player, NumberOfResources toDiscard){
        super(player);
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
            //TODO error messahe
            return;
        }catch (UnableToFillException e){
            //TODO error message
            return;
        }


        player.setStatus(PlayerStatus.MovePerformed);
    }
}
