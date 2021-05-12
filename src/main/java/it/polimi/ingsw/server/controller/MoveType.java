package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Move2;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public abstract class MoveType {
    //raccogliere tutte le info chieste al giocatore e chiama l'update del controller

    private final int idPlayer;
    PlayerStatus[] allowedStatus;

    public MoveType(int idPlayer){
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public abstract String getClassName();

    /**
     * @return true if the player is in the correct status and it's his turn
     */
    public boolean canPerform(Game game){
        Player player = game.getPlayerFromID(idPlayer);
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

    @Override
    public boolean equals(Object other){
        if(other==this)
            return true;

        if(!(other instanceof MoveType))
            return false;

        return this.idPlayer == ((MoveType) other).getIdPlayer();
    }

}
