package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public abstract class MoveType {
    //raccogliere tutte le info chieste al giocatore e chiama l'update del controller

    Player player;
    PlayerStatus[] allowedStatus;
    private static String className;

    public MoveType(Player player, String className){
        this.player = player;
        this.className=className;
    }

    public Player getPlayer() {
        return player;
    }

    public String getClassName() {
        return className;
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
