package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.message.GameMessage;
import it.polimi.ingsw.constant.message.LastMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.*;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.view.View;


import java.util.*;
/*Last Edit: Gio*/
/**
 * Superclasse che gestisce tutto il gioco
 */
public class GameExt extends Game implements Observable<Message> {

    private transient final ArrayList<LeaderCardExt> leaderCards;

    /**
     * Default constructor
     */
    public GameExt(ArrayList<PlayerExt> players, MarketExt market, DashboardExt dashboard, ArrayList<SoloActionTokens> soloActionTokens, ArrayList<LeaderCardExt> leaderCards){
        if(players.isEmpty())
            throw new IllegalArgumentException();

        if(players.size()*Game.INITIAL_LEADER_CARD> leaderCards.size() || players.size()>Game.MAX_PLAYER)
            throw  new IllegalArgumentException();


        if(players.size()==1){
            //single player mode
            super.setSoloGame(new LorenzoSoloPlayerExt(this, new FaithTrackExt(-1), soloActionTokens));
        }
        else{
            super.setSoloGame(null);
        }
        this.leaderCards = new ArrayList<>(leaderCards);
        Collections.shuffle(this.leaderCards);
        super.setMarketTray(market);
        super.setDashboard(dashboard);
        super.setStatus(GameStatus.Initial);

        super.setPlayers(new ArrayList<>(players));
        Collections.shuffle(players);

        for(int i =0; i<players.size(); i++){
            super.getPlayers().get(i).getFaithTrack().setFaithPoints(Game.INITIAL_FAITH_POINT[i]);
        }
    }

    @Override
    public MarketExt getMarketTray() {
        return (MarketExt) super.getMarketTray();
    }

    @Override
    public DashboardExt getDashboard() {
        return (DashboardExt) super.getDashboard();
    }

    @Override
    public LorenzoSoloPlayerExt getSoloGame() {
        return (LorenzoSoloPlayerExt) super.getSoloGame();
    }

    @Override
    public PlayerExt getPlayerFromID(int id) {
        return (PlayerExt)super.getPlayerFromID(id);
    }

    public PlayerExt getPlayer(int index){
        return (PlayerExt) super.getPlayers().get(index);
    }

    @Override
    public PlayerExt getCurrPlayer() {
        return (PlayerExt) super.getCurrPlayer();
    }

    public void popesInspection(){
        int index = -1;
        if(super.getPlayers().size()==1){
            //single player
            int temp = ((FaithTrackExt)super.getSoloGame().getFaithTrack()).inspectionNeed();
            if(temp>=0)
                index = temp;
        }
        for(Player p : super.getPlayers()){
            int temp = ((FaithTrackExt)p.getFaithTrack()).inspectionNeed();
            if(temp>=0)
                index = temp;
        }
        //real pope's inspection
        if(index >=0){
            if(super.getPlayers().size()==1){
                ((FaithTrackExt)super.getSoloGame().getFaithTrack()).popeInspection(index);
            }
            for(Player p : super.getPlayers()){
                ((FaithTrackExt)p.getFaithTrack()).popeInspection(index);
            }
        }
    }

    public void nextTurn(){
        super.getCurrPlayer().setStatus(PlayerStatus.Waiting);
        if(super.isSinglePlayer()){
            //single player
            super.getCurrPlayer().setStatus(PlayerStatus.Active);
            if(((FaithTrackExt)super.getCurrPlayer().getFaithTrack()).isEnd() || super.getCurrPlayer().getPersonalBoard().isFull()){
                super.setStatus(GameStatus.Ended);
                getPlayer(0).setStatus(PlayerStatus.Waiting);
            }
            ((LorenzoSoloPlayerExt)super.getSoloGame()).revealToken();
            if(((FaithTrackExt)super.getSoloGame().getFaithTrack()).isEnd() || getDashboard().isEmpty()){
                super.setStatus(GameStatus.Ended);
                super.getSoloGame().setWinner();
                getPlayer(0).setStatus(PlayerStatus.Waiting);
            }
        }
        else {
            if(super.getStatus() == GameStatus.Running){
                //check for final turn
                if(((FaithTrackExt)super.getCurrPlayer().getFaithTrack()).isEnd() || super.getCurrPlayer().getPersonalBoard().isFull()){
                    super.setStatus(GameStatus.LastTurn);
                }
            }
            super.increaseIndex();
            if (super.getCurrIndex() == 0 && super.getStatus() == GameStatus.LastTurn) {
                super.setStatus(GameStatus.Ended);
            } else {
                super.getCurrPlayer().setStatus(PlayerStatus.Active);
            }
        }
        change();
    }


    public void updateStatus(){
        if(super.getStatus()==GameStatus.Initial) {
            boolean needToUpdate = true;
            for (Player p : super.getPlayers()) {
                if(!p.getPersonalBoard().isReady()  || p.getDepots().getResources().size()!=getInitialResources(p.getID()))
                    needToUpdate = false;
            }
            if(needToUpdate) {
                super.setStatus(GameStatus.Running);
                super.getPlayers().get(0).setStatus(PlayerStatus.Active);
                super.setIndexPlayingPlayer(0);
                change();
            }else if(getCurrPlayer().getPersonalBoard().isReady()  && getCurrPlayer().getDepots().getResources().size()==getInitialResources(getCurrPlayer().getID())){
                super.increaseIndex();
                change();
            }
        }
    }

    public ArrayList<LeaderCard> getActivableLeadCard(Player player){
        int index = super.getPlayerIndex(player)* INITIAL_LEADER_CARD;
        if(index<0)
            throw new IllegalArgumentException();

        ArrayList<LeaderCard> res= new ArrayList<>();

        for(int i=0; i<INITIAL_LEADER_CARD; i++){
            res.add(leaderCards.get(index + i));
        }

        return res;
    }

    /**@return the player Id from his nickname, -1 otherwise*/
    public int getPlayerIdfromNickname(String nickName){
        for (Player player: getPlayers()){
            if(player.getUserName().equals(nickName)){
                return player.getID();
            }
        }
        return -1;
    }

    public Player getPlayerFromNickname(String nickName){
        for (Player player: getPlayers()){
            if(player.getUserName().equals(nickName)){
                return player;
            }
        }
        return null;
    }
    /**
     *
     * @param id to search for
     * @return a reference to the leader card with the wanted Id, null otherwise
     */
    public LeaderCardExt findLeaderCard(int id){
        for(LeaderCardExt x: leaderCards){
            if(x.getId()==id){
                return x;
            }
        }
        return null;
    }

    public ArrayList<LeaderCardExt> findMoreLeaderCard(ArrayList<Integer> cardId){
        ArrayList<LeaderCardExt> leaderCards= new ArrayList<>();
        for (int id: cardId){
            leaderCards.add(findLeaderCard(id));

        }

        return leaderCards;
    }

    private void change(){
        notify(new GameMessage(getStatus(), getCurrIndex(), this.getSoloGame()));
    }

    public void lastMessage(){
        notify(new LastMessage());
    }
    //Observable implementation
    private transient final List<Observer<Message>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<Message> observer){
       synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for(Observer<Message> observer : observers){
                observer.update(message);
            }
        }
    }
}