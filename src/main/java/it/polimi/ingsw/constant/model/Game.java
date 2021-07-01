package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.GameStatus;

import java.util.ArrayList;

/**
 * Game abstract class.
 * Superclass of GameExt (server) and GameClient (client).
 * Manage the all game.
 */
public abstract class Game {

    public static final int MAX_PLAYER = 4;
    public static final int INITIAL_LEADER_CARD = 4;
    public static final int[] INITIAL_FAITH_POINT = {0,0,1,1};
    public static final int[] INITIAL_RESOURCES = {0,1,1,2};

    //compositions
    private ArrayList<Player> players;
    private Market marketTray;
    private Dashboard dashboard;
    private LorenzoSoloPlayer soloGame;

    //attribute
    private int indexPlayingPlayer;
    private GameStatus status;


    /**
     * Gets market tray.
     *
     * @return of type Market: the market tray.
     */
//getter
    public Market getMarketTray() {
        return marketTray;
    }

    /**
     * Get players array list.
     *
     * @return of type ArrayList<Player>: the array list.
     */
    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(this.players);
    }

    /**
     * Gets dashboard.
     *
     * @return of type Dashboard: the dashboard.
     */
    public Dashboard getDashboard() {
        return dashboard;
    }

    /**
     * Gets solo game.
     *
     * @return of type LorenzoSoloPlayer: the solo game
     */
    public LorenzoSoloPlayer getSoloGame() {
        return soloGame;
    }

    /**
     * Gets curr player's index in the list.
     *
     * @return of type int: the curr index.
     */
    public int getCurrIndex() {
        return indexPlayingPlayer;
    }

    /**
     * Gets game's status.
     *
     * @return of type GameStatus: the status.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Get player index from a player.
     *
     * @param player of type Player: the player.
     * @return of type int: the index of the list.
     */
//smart getter
    public int getPlayerIndex(Player player){
        return players.indexOf(player);
    }

    /**
     * Gets initial resources.
     *
     * @param myID of type int: the my id.
     * @return of type int: the number of initial resources.
     */
    public int getInitialResources(int myID) {
        return INITIAL_RESOURCES[getPlayerIndex(getPlayerFromID(myID))];
    }

    /**
     * Gets curr player.
     *
     * @return of type Player: a reference to the only player witch state is Active.
     */
    public Player getCurrPlayer() {
        if(status == GameStatus.Ended) return null;
        return players.get(indexPlayingPlayer);
    }

    /**
     * Gets winner.
     *
     * @return of type Player: a reference to the winner player.
     * @throws IllegalArgumentException if game is not ended.
     * @null if Lorenzo is the Winner in soloplayer.
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
     * Check if it is single player game.
     *
     * @return of type boolean: True if the game is a single player game. Otherwise False.
     */
    public boolean isSinglePlayer(){
        return players.size()==1;
    }

    /**
     * Get player from an id player.
     *
     * @param id of type int: the player's id to look for.
     * @return of type Player: a reference to the player, null if she does not exist.
     */
    public Player getPlayerFromID(int id){
        for(Player p : players){
            if(p.getID()==id)
                return p;
        }
        return null;
    }

    //setter

    /**
     * Set status.
     *
     * @param status of type GameStaus: the status.
     */
    public void setStatus(GameStatus status){
        this.status = status;
    }

    /**
     * Set index.
     *
     * @param index of type int: the index.
     */
    public void setIndex(int index){
        this.indexPlayingPlayer= index;
    }

    /**
     * Sets dashboard.
     *
     * @param dashboard of type Dashboard: the dashboard.
     */
    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Sets players.
     *
     * @param players of type ArrayList<Player>: the players.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Sets market tray.
     *
     * @param marketTray of type Market: the market tray.
     */
    public void setMarketTray(Market marketTray) {
        this.marketTray = marketTray;
    }

    /**
     * Sets solo game.
     *
     * @param soloGame of type LorenzoSoloPlayer: the solo game.
     */
    public void setSoloGame(LorenzoSoloPlayer soloGame) {
        this.soloGame = soloGame;
    }

    /**
     * Sets index playing player.
     *
     * @param indexPlayingPlayer of type int: the index playing player.
     */
    public void setIndexPlayingPlayer(int indexPlayingPlayer) {
        this.indexPlayingPlayer = indexPlayingPlayer;
    }

    /**
     * Increase index.
     */
    public void increaseIndex(){
        indexPlayingPlayer++;
        indexPlayingPlayer = indexPlayingPlayer%players.size();
    }
}