package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.GameStatus;

import java.util.ArrayList;
/*Last Edit: Gio*/

/**
 * Superclasse che gestisce tutto il gioco
 */
public abstract class Game {

    public static final int MAX_PLAYER = 4;
    public static final int INITIAL_LEADER_CARD = 4;
    public static final int[] INITIAL_FAITH_POINT = {0,1,2,3};
    public static final int[] INITIAL_RESOURCES = {0,1,1,2};

    //compositions
    private ArrayList<Player> players;
    private Market marketTray;
    private Dashboard dashboard;
    private LorenzoSoloPlayer soloGame;

    //attribute
    private int indexPlayingPlayer;
    private GameStatus status;


    //getter
    public Market getMarketTray() {
        return marketTray;
    }

    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(this.players);
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public LorenzoSoloPlayer getSoloGame() {
        return soloGame;
    }

    public int getCurrIndex() {
        return indexPlayingPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    //smart getter
    public int getPlayerIndex(Player player){
        return players.indexOf(player);
    }

    public int getInitialResources(int myID) {
        return INITIAL_RESOURCES[getPlayerIndex(getPlayerFromID(myID))];
    }
    /**
     * @return a reference to the only player witch state is Active
     */
    public Player getCurrPlayer() {
        if(status == GameStatus.Ended) return null;
        return players.get(indexPlayingPlayer);
    }

    /**
     * @return a reference to the winner player
     * @null if Lorenzo is the Winner in soloplayer
     * @throws IllegalArgumentException if game is not ended
     */
    public Player getWinner() throws IllegalArgumentException{
        if(status != GameStatus.Ended){
            throw new IllegalArgumentException();
        }
        Player winner = null;
        if(players.size()>1) {
            //multiplayer
            int max = -1;
            for(Player p: players){
                if(p.getVictoryPoints()>max){
                    max = p.getVictoryPoints();
                    winner = p;
                }
            }
        }
        else{
            if(!soloGame.isWinner())
                winner=players.get(0);
        }

        return winner;
    }

    /**
     * @return true if the game is a single player game
     */
    public boolean isSinglePlayer(){
        return players.size()==1;
    }

    /**
     * @param id of type int - the player's id to look for
     * @return a reference to the player, null if she does not exist
     */
    public Player getPlayerFromID(int id){
        for(Player p : players){
            if(p.getID()==id)
                return p;
        }
        return null;
    }

    //setter

    public void setStatus(GameStatus status){
        this.status = status;
    }

    public void setIndex(int index){
        this.indexPlayingPlayer= index;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setMarketTray(Market marketTray) {
        this.marketTray = marketTray;
    }

    public void setSoloGame(LorenzoSoloPlayer soloGame) {
        this.soloGame = soloGame;
    }

    public void setIndexPlayingPlayer(int indexPlayingPlayer) {
        this.indexPlayingPlayer = indexPlayingPlayer;
    }

    public void increaseIndex(){
        indexPlayingPlayer++;
        indexPlayingPlayer = indexPlayingPlayer%players.size();
    }
}