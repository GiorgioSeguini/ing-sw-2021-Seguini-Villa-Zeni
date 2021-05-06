package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Move2;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public abstract class MoveType {
    //raccogliere tutte le info chieste al giocatore e chiama l'update del controller

    private int idPlayer;
    PlayerStatus[] allowedStatus;
    private final String ClassName;

    public MoveType(int idPlayer, String className){
        this.idPlayer = idPlayer;
        this.ClassName=className;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public String getClassName() {
        return ClassName;
    }

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

        if(this.idPlayer!= ((MoveType) other).getIdPlayer()){
            return false;
        }

        return this.ClassName.equals(((MoveType) other).getClassName());
    }

}
