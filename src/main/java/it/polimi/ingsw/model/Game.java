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
    private Set<Player> players;

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
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Player getCurrPlayer() {
        // TODO implement here
        return null;
    }

    /**
     * @param player 
     * @return
     */
    public int getPlayerIndex(Player player) {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public int getCurrIndex() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public void start() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Player getWinner() {
        // TODO implement here
        return null;
    }

}