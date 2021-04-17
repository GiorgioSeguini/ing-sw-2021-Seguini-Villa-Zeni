package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillException;

import javax.swing.text.View;

public class MoveDiscardResources extends MoveType{
    private final NumberOfResources toDiscard;

    public MoveDiscardResources(Player player, View view, boolean isLastMove, NumberOfResources toDiscard){
        super(player);
        this.toDiscard=toDiscard;
    }

    public NumberOfResources getToDiscard() {
        return toDiscard;
    }

    @Override
    public boolean canPerform(Game game){
        if (!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player = game.getCurrPlayer();
        if (player.getStatus() != PlayerStatus.NeedToStore) {
            //TODO error Message
            return false;
        }

        return true;
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
