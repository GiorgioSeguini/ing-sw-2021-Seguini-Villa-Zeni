package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Player;

import java.util.Scanner;

public abstract class MoveType {

    private final int idPlayer;

    public MoveType(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
    }


    public abstract boolean canPerform(GameClient game);

    public void updateCLI(Game game, Scanner stdin){
        System.out.println("Move not yet implemented");
        //TODO abstract method
    }

    public abstract String getClassName();


    protected boolean simpleCheck(GameClient game, PlayerStatus[] allowedStatus){
        Player player = game.getPlayerFromID(idPlayer);
        if(player==null)
            return false;

        if(game.getStatus()!= GameStatus.Running && game.getStatus()!=GameStatus.LastTurn){
            return false;
        }
        if(!game.isMyTurn()) {
            return false;
        }

        boolean goodStatus=false;
        for(PlayerStatus status : allowedStatus) {
            if (player.getStatus() == status) {
                goodStatus=true;
            }
        }
        return goodStatus;
    }


    protected boolean initialMove(GameClient game){
        Player player = game.getPlayerFromID(idPlayer);
        if(player==null)
            return false;

        if(game.getStatus() != GameStatus.Initial){
            return false;
        }

        return game.isMyTurn();
    }
    @Override
    public boolean equals(Object other){
        if(other==this)
            return true;

        if(!(other instanceof MoveType))
            return false;

        return this.idPlayer == ((MoveType) other).getIdPlayer();
        //TODO rivedere
    }

}
