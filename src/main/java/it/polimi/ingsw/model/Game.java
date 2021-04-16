package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.GameStatus;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.exception.FinalTurnException;

import java.util.*;
/*Last Edit: Gio*/
/**
 * Superclasse che gestisce tutto il gioco
 */
public class Game {

    private static final int INITIAL_LEADER_CARD = 4;

    //compositions
    private final ArrayList<Player> players;
    private final Market marketTray;
    private final Dashboard dashboard;
    private final LorenzoSoloPlayer soloGame;
    private final ArrayList<LeaderCard> leaderCards;

    //attribute
    private int indexPlayingPlayer;
    private GameStatus status;

    /**
     * Default constructor
     */
    public Game(ArrayList<Player> players, Market market, Dashboard dashboard, ArrayList<SoloActionTokens> soloActionTokens, ArrayList<LeaderCard> leaderCards){
        if(players.isEmpty())
            throw new IllegalArgumentException();
        for(Player p : players){
            if(p.getPersonalBoard().isReady())
                throw new IllegalArgumentException();
        }
        if(players.size()*INITIAL_LEADER_CARD> leaderCards.size())
            throw  new IllegalArgumentException();

        if(players.size()==1){
            //single player mode
            soloGame = new LorenzoSoloPlayer(this, soloActionTokens);
        }
        else{
            soloGame = null;
        }
        this.leaderCards = leaderCards;
        this.players=players;
        this.marketTray = market;
        this.dashboard = dashboard;
        this.status = GameStatus.Initial;

        Collections.shuffle(players);
        Collections.shuffle(leaderCards);
    }

    public Market getMarketTray() {
        return marketTray;
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
        if(status==GameStatus.Initial || status == GameStatus.Ended) return null;
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
    public Player getWinner() throws IllegalArgumentException{
        if(status != GameStatus.LastTurn || indexPlayingPlayer!=0){
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
        status = GameStatus.LastTurn;
    }

    public boolean isFinalTurn() {
        return status == GameStatus.LastTurn;
    }

    public void popesInspection(){
        int index = -1;
        if(players.size()==1){
            int temp = soloGame.getFaithTrack().inspectionNeed();
            if(temp>=0)
                index = temp;
        }
        for(Player p : players){
            int temp = p.getFaithTrack().inspectionNeed();
            if(temp>=0)
                index = temp;
        }

        if(index >=0){
            if(players.size()==1){
                try {
                    soloGame.getFaithTrack().popeInspection(index);
                }catch(FinalTurnException e){
                    //TODO Lorenzo il maginfico wins the game!
                }
            }
            for(Player p : players){
                try{
                    p.getFaithTrack().popeInspection(index);
                }catch(FinalTurnException e){
                    //TODO eventualmente si può rilanciare al chiamante
                    setFinalTurn();
                }
            }
        }
    }

    public void nextTurn(){
        players.get(indexPlayingPlayer).setStatus(PlayerStatus.Waiting);
        indexPlayingPlayer++;
        indexPlayingPlayer%=players.size();
        players.get(indexPlayingPlayer).setStatus(PlayerStatus.Active);
    }

    public GameStatus getStatus() {
        return status;
    }

    public void updateStatus(){
        if(status==GameStatus.Initial) {
            boolean needToUpdate = true;
            for (Player p : players) {
                if(!p.getPersonalBoard().isReady())
                    needToUpdate = false;
            }
            if(needToUpdate)
                status=GameStatus.Running;
        }
    }

    public ArrayList<LeaderCard> getActivableLeadCard(Player player){
        int index = getPlayerIndex(player)* INITIAL_LEADER_CARD;
        if(index<0)
            throw new IllegalArgumentException();

        ArrayList<LeaderCard> res= new ArrayList<>();

        for(int i=0; i<INITIAL_LEADER_CARD; i++){
            res.add(leaderCards.get(index + i));
        }

        return res;
    }
}