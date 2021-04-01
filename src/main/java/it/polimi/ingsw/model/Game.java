package it.polimi.ingsw.model;

import java.util.*;
/*Last Edit: Gio*/
/**
 * Superclasse che gestisce tutto il gioco
 */
public class Game {

    //compositions
    private ArrayList<Player> players;
    private Market marketTray;
    private Dashboard dashboard;
    private LorenzoSoloPlayer soloGame;

    //attribute
    private int indexPlayingPlayer;
    private boolean finalTurn;

    /**
     * Default constructor
     */
    public Game() {
    }




    /**
     * @param index 
     * @return
     */
    public Player getPlayer(int index) {
        return players.get(index);
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
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

    public Dashboard getDashboard() {
        return dashboard;
    }

    public LorenzoSoloPlayer getSoloGame() {
        return soloGame;
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
    public Player getWinner() throws IllegalArgumentException{
        //TODO check exception
        if(!finalTurn || indexPlayingPlayer!=0){
            throw new IllegalArgumentException();
        }
        Player winner;
        if(soloGame == null) {
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

    public void popesIspection(){};

}