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
    public abstract boolean canPerform(Game game);

    public abstract void performMove(Game game);

    @Override
    public boolean equals(Object other){
        if(other==this)
            return true;

        if(!(other instanceof MoveType))
            return false;

        return this.idPlayer == ((MoveType) other).getIdPlayer();
    }

    protected boolean simpleCheck(Game game, PlayerStatus[] allowedStatus){
        Player player = game.getPlayerFromID(idPlayer);
        if(player==null)
            return false;

        if(game.getStatus()!= GameStatus.Running && game.getStatus()!=GameStatus.LastTurn){
            player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }
        if(!game.getCurrPlayer().equals(player)) {
            player.setErrorMessage(ErrorMessage.NotYourTurn);
            return false;
        }

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

    protected boolean initialMove(Game game){
        Player player = game.getPlayerFromID(idPlayer);
        if(player==null)
            return false;

        if(game.getStatus() != GameStatus.Initial){
            player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }

        if(!game.getCurrPlayer().equals(player)) {
            player.setErrorMessage(ErrorMessage.NotYourTurn);
            return false;
        }

        return true;
    }
}
