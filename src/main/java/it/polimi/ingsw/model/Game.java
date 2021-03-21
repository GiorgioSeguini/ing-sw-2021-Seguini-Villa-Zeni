package it.polimi.ingsw.model;

import java.util.*;

/**
 * Superclasse che gestisce tutto il gioco
 */
public class Game {

    /**
     * Default constructor
     */
    public Game() {
    }

    /**
     * Intero che descrive il giocatore in gioco da 0-3. Player0 è quello che ha il calamaio e inizia il gioco. 
     * 
     * (forse anche qui sarebbe meglio un enumerazione con id associati?)
     */
    private int indexPlayingPlayer;

    /**
     * booleano che mi indica se il turno è l'ultimo giocabile. Questo avviene perchè quando il gioco termina, ogni giocatore ha diritto di giocare il proprio turno. A fine di quel turno il gioco termina.
     */
    private boolean finalTurn;

    /**
     * 
     */
    private ArrayList<Player> players;

    /**
     * 
     */
    private Market marketTray;

    /**
     * 
     */
    private Dashboard dashboard;

    /**
     * 
     */
    private LorenzoSoloPlayer sologame;

    /**
     * @param index 
     * @return
     */
    public Player getPlayer(int index) {
        return players.get(index);
    }

    /**
     * @return
     */
    public Player getCurrPlayer() {
        return players.get(indexPlayingPlayer);
    }

    /**
     * @param player 
     * @return
     */
    public int getPlayerIndex(Player player) {
        return players.indexOf(player);
    }

    /**
     * @return
     */
    public int getCurrIndex() {
        return indexPlayingPlayer;
    }

    /**
     * @return
     */
    public void start() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Player getWinner() throws Exception{
        //TODO check exception
        if(!finalTurn || indexPlayingPlayer!=0){
            throw new Exception();
        }
        Player winner;
        if(sologame != null) {
            //multiplayer
            int max = players.get(0).getVictoryPoints();
            winner = players.get(0);
            for(Player p: players){
                if(p.getVictoryPoints()>max){
                    max = p.getVictoryPoints();
                    winner = p;
                }
            }
        }
        else{
            //single player
            //TODO
            winner=players.get(0);
        }

        return winner;
    }

    public void setFinalTurn() {
        this.finalTurn = true;
    }

    public boolean isFinalTurn() {
        return finalTurn;
    }
}