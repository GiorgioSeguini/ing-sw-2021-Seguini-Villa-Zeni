package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.message.GameMessage;
import it.polimi.ingsw.constant.message.InitialMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.parse.Starter;


import java.util.*;
/*Last Edit: Gio*/
/**
 * Superclasse che gestisce tutto il gioco
 */
public class Game extends Observable<Message> {

    private static final int MAX_PLAYER = 4;
    private static final int INITIAL_LEADER_CARD = 4;
    private static final int[] INITIAL_RESOURCES = {0,1,1,2};
    private static final int[] INITIAL_FAITH_POINT = {0,0,1,1};

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

        if(players.size()*INITIAL_LEADER_CARD> leaderCards.size() || players.size()>MAX_PLAYER)
            throw  new IllegalArgumentException();

        if(players.size()==1){
            //single player mode
            soloGame = new LorenzoSoloPlayer(this, soloActionTokens);
        }
        else{
            soloGame = null;
        }
        this.leaderCards = new ArrayList<>(leaderCards);
        Collections.shuffle(this.leaderCards);
        this.marketTray = market;
        this.dashboard = dashboard;
        this.status = GameStatus.Initial;

        this.players = new ArrayList<>(players);
        Collections.shuffle(players);

        for(int i =0; i<players.size(); i++){
            for(int j=0; j<INITIAL_FAITH_POINT[i]; j++)
                this.players.get(i).getFaithTrack().addPoint();
        }
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
        return new ArrayList<>(this.players);
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

    public void popesInspection(){
        int index = -1;
        if(players.size()==1){
            //single player
            int temp = soloGame.getFaithTrack().inspectionNeed();
            if(temp>=0)
                index = temp;
        }
        for(Player p : players){
            int temp = p.getFaithTrack().inspectionNeed();
            if(temp>=0)
                index = temp;
        }
        //real pope's inspection
        if(index >=0){
            if(players.size()==1){
                soloGame.getFaithTrack().popeInspection(index);
            }
            for(Player p : players){
                p.getFaithTrack().popeInspection(index);
            }
        }
    }

    public void nextTurn(){
        players.get(indexPlayingPlayer).setStatus(PlayerStatus.Waiting);
        if(players.size()==1){
            //single player
            if(players.get(indexPlayingPlayer).getFaithTrack().isEnd() || players.get(indexPlayingPlayer).getPersonalBoard().isFull()){
                status=GameStatus.Ended;
            }
            soloGame.revealToken();
            if(soloGame.getFaithTrack().isEnd() || getDashboard().isEmpty()){
                status=GameStatus.Ended;
                soloGame.setWinner();
            }
        }
        else {
            if(status == GameStatus.Running){
                //check for final turn
                if(players.get(indexPlayingPlayer).getFaithTrack().isEnd() || players.get(indexPlayingPlayer).getPersonalBoard().isFull()){
                    status=GameStatus.LastTurn;
                }
            }
            indexPlayingPlayer++;
            if (indexPlayingPlayer == players.size()) {
                indexPlayingPlayer = 0;
                if (status == GameStatus.LastTurn) {
                    status = GameStatus.Ended;
                } else {
                    players.get(indexPlayingPlayer).setStatus(PlayerStatus.Active);
                }
            }
        }
        change();
    }

    public GameStatus getStatus() {
        return status;
    }

    public void updateStatus(){
        if(status==GameStatus.Initial) {
            boolean needToUpdate = true;
            for (Player p : players) {
                if(!p.getPersonalBoard().isReady()  || p.getDepots().getResources().size()!=getInitialResources(p))
                    needToUpdate = false;
            }
            if(needToUpdate) {
                status = GameStatus.Running;
                players.get(0).setStatus(PlayerStatus.Active);
                change();
            }
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

    public int getInitialResources(Player player){
        int index = getPlayerIndex(player);
        if(index<0)
            throw new IllegalArgumentException();

        return INITIAL_RESOURCES[index];
    }

    public LeaderCard findLeaderCard(int id){
        for(LeaderCard x: leaderCards){
            if(x.getId()==id){
                return x;
            }
        }
        return null;
    }

    public ArrayList<LeaderCard> findMoreLeaderCard(ArrayList<Integer> cardId){
        ArrayList<LeaderCard> leaderCards= new ArrayList<>();
        for (int id: cardId){
            leaderCards.add(findLeaderCard(id));

        }

        return leaderCards;
    }

    public Player getPlayerFromID(int ID){
        for(Player player : getPlayers()){
            if(player.getID()==ID)
                return player;
        }
        return null;
    }


    private void change(){
        notify(new GameMessage(getStatus(), getCurrIndex()));
    }

    public void start(){
        //notify(new InitialMessage(Starter.toJson(this, InitialMessage.class))); // TODO: 5/6/21 da rivedere
    }
}