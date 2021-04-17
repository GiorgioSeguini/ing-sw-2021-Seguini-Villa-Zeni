package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
import it.polimi.ingsw.model.enumeration.PlayerStatus;

import javax.swing.text.View;

public abstract class MoveType {
    //raccogliere tutte le info chieste al giocatore e chiama l'update del controller

    Player player;
    PlayerStatus[] allowedStatus;

    public MoveType(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * @return true if the player is in the correct status and it's his turn
     */
    public boolean canPerform(Game game){
        if(!game.getCurrPlayer().equals(player)) {
            player.setErrorMessage(ErrorMessage.NotYourTurn);
            return false;
        }

        player= game.getCurrPlayer();

        boolean goodStatus=false;
        for(PlayerStatus status : allowedStatus) {
            if (player.getStatus() == status) {
                goodStatus=true;
            }
        }
        if(!goodStatus){
            player.setErrorMessage(ErrorMessage.MoveNotAllowed);
        }

        return goodStatus;
    }

    public abstract void performMove(Game game);

}
