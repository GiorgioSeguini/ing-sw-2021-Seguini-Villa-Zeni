package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;

/**
 * Abstract class that represent a possible move of a player and a possible message from client to server
 * Include some standard methods reused in an appropriate way
 * Each class that inherits must have an unique static attribute className of type String
 * Before send each move to the server you must set all the possible parameter, otherwise the server will reject your move
 */
public abstract class MoveType {

    private final int idPlayer;

    /**
     * Default COnstructor
     * @param idPlayer of type int - the id of the player that execute the move
     */
    public MoveType(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    /**
     * Getter
     * @return int - the id of the player that execute the move
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * Simple check executed also on client side
     * @param game of type Game - the game reference
     * @return true if player status  and game status allow move execution, false otherwise - do not ensure the move is possible
     */
    public abstract boolean canPerform(Game game);

    /**
     * For serialization
     * @return className of type String - the static value of the attribute className
     */
    public abstract String getClassName();

    /**
     * @param game of type Game - the game
     * @param allowedStatus of type PlayerStatus[] - an array containing the allowed status of players
     * @return true if the player is part of the game and he is in any allowed status, false otherwise
     */
    protected boolean simpleCheck(Game game, PlayerStatus[] allowedStatus){
        Player player = game.getPlayerFromID(idPlayer);
        if(player==null)
            return false;

        if(game.getStatus()!= GameStatus.Running && game.getStatus()!=GameStatus.LastTurn){
            return false;
        }
        if(game.getCurrPlayer().getID() != idPlayer) {
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

    /**
     *
     * @param game of type Game - the game
     * @return true if the player is part of the game and the game is the initial status, false otherwise
     */
    protected boolean initialMove(Game game){
        Player player = game.getPlayerFromID(idPlayer);
        if(player==null)
            return false;

        if(game.getStatus() != GameStatus.Initial){
            return false;
        }

        return game.getCurrPlayer().getID() == idPlayer;
    }

    @Override
    public boolean equals(Object other){
        if(other==this)
            return true;

        if(!(other instanceof MoveType))
            return false;

        return this.idPlayer == ((MoveType) other).getIdPlayer();
    }

}
