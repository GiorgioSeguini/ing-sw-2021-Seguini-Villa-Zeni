package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.GameStatus;

import java.util.ArrayList;
/*Last Edit: Gio*/

/**
 * Superclasse che gestisce tutto il gioco
 */
public class Game {

    private static final int[] INITIAL_FAITH_POINT = {0,1,2,3};

    //compositions
    private final ArrayList<Player> players;
    private final Market marketTray;
    private Dashboard dashboard;
    private final LorenzoSoloPlayer soloGame;

    //attribute
    private int indexPlayingPlayer;
    private GameStatus status;

    /**
     * Default constructor
     */
    public Game(ArrayList<String> playersName, Market market, Dashboard dashboard, LorenzoSoloPlayer soloGame){
        if(playersName.isEmpty())
            throw new IllegalArgumentException();

        this.marketTray = market;
        this.dashboard = dashboard;
        this.status = GameStatus.Initial;
        this.soloGame = soloGame;

        this.players = new ArrayList<>();
        for (String s : playersName) {
            Player player = new Player(s);
            this.players.add(player);
        }
    }

    public Market getMarketTray() {
        return marketTray;
    }

    /**
     * @param index of the player
     * @return if present a reference o player index, otherwise null
     */
    public Player getPlayer(int index) {
        return players.get(index);
    }

    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(this.players);
    }

    /**
     * @return a reference to the only player witch state is Active
     */
    public Player getCurrPlayer() {
        if(status== GameStatus.Initial || status == GameStatus.Ended) return null;
        return players.get(indexPlayingPlayer);
    }

    /**
     * @param player player
     * @return the index corresponding to the player
     */
    public int getPlayerIndex(Player player) {
        return players.indexOf(player);
    }

    /**
     * @return index of the current player
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
     * @return a reference to the winner player
     * @throws IllegalArgumentException if game is not ended
     */
    public Player getWinner() throws IllegalArgumentException{
        if(status != GameStatus.Ended){
            throw new IllegalArgumentException();
        }
        Player winner = null;
        if(players.size()>1) {
            //multiplayer
            int max = 0;
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

    public GameStatus getStatus() {
        return status;
    }

    public Player getPlayerFromID(int ID){
        for(Player player : getPlayers()){
            if(player.getID()==ID)
                return player;
        }
        return null;
    }

    public void setStatus(GameStatus status){
        this.status = status;
    }

    public void setIndex(int index){
        this.indexPlayingPlayer= index;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
}