package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.view.RemoteView;
import it.polimi.ingsw.server.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static final int PORT = 12345;
    private static final int NUMOFPOSSIBLEGAMES= 4;
    private final ServerSocket serverSocket;
    private final ArrayList<Map<String, ClientConnection>> waitingConnections = new ArrayList<>();
    private final Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private ArrayList<String> playersNickNames= new ArrayList<>();
    private ArrayList<Room> rooms= new ArrayList<>();

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        //Iterator<String> iterator = waitingConnection.keySet().iterator();
       // while(iterator.hasNext()){
            //if(waitingConnection.get(iterator.next())==c){
         //       iterator.remove();
            //}
        // TODO: 5/20/21
        //}
    }

    public synchronized boolean checkPlayerName(String name){
        for(String x: playersNickNames){
            if(x.equals(name)){
                return false;
            }
        }
        return true;
    }

    public synchronized boolean checkRoomsName(String name){
        for(Room x: rooms ){
            if(x.getRoomName().equals(name)){
                return false;
            }
        }
        return true;
    }

    //Wait for another player
    public synchronized boolean lobby(ClientConnection c, String name, int numofplayer){

        if(numofplayer<1 || numofplayer>4){
            throw  new IllegalArgumentException();
        }
        waitingConnections.get(numofplayer-1).put(name,c);
        playersNickNames.add(name);

        if (waitingConnections.get(numofplayer-1).size()>=numofplayer) {
            startGame(waitingConnections.get(numofplayer-1));
        }
        return true;
    }

    private void startGame(Map<String, ClientConnection> waitingConnection){
        ArrayList<PlayerExt> players=getPlayersforGame(waitingConnection);
        GameExt game = null;

        try {
            game=new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), Starter.TokensParser(), Starter.LeaderCardsParser());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //TODO
        }
        Controller controller = new Controller(game);
        ArrayList<View> playersView = instanceViews(waitingConnection,game.getPlayers());

        addObserverGame(playersView, game, controller);


        ArrayList<ClientConnection> connections= getConnectionforGame(waitingConnection, game.getPlayers());
        makeConnection(connections);

        waitingConnection.clear();

        //send initial message
        for(View view : playersView) {
            view.sendInitialMessage(game);
        }
    }

    private void addObserverGame(ArrayList<View> playersView, GameExt game, Controller controller) {
        for(View view : playersView){
            //add model - view links
            game.getMarketTray().addObserver(view);
            game.getDashboard().addObserver(view);
            game.addObserver(view);
            for(Player player : game.getPlayers()){
                PlayerExt playerExt = (PlayerExt) player;
                playerExt.getPersonalBoard().addObserver(view);
                playerExt.getFaithTrack().addObserver(view);
                playerExt.getDepots().addObserver(view);
                playerExt.addObserver(view);
                playerExt.getConverter().addObserver(view);
            }

            //add controller - view links
            view.addObserver(controller);
        }
    }

    private ArrayList<View> instanceViews(Map<String, ClientConnection> waitingConnection, ArrayList<Player> players) {
        ArrayList<View> playersView =new ArrayList<>();
        for(Player player: players){
            playersView.add(new RemoteView(player,waitingConnection.get(player.getUserName())));
        }
        return playersView;
    }

    private ArrayList<ClientConnection> getConnectionforGame(Map<String, ClientConnection> waitingConnection, ArrayList<Player> players) {
        ArrayList<ClientConnection> connections= new ArrayList<>();
        for (Player player: players){
            connections.add(waitingConnection.get(player.getUserName()));
        }
        return connections;
    }

    private void makeConnection(ArrayList<ClientConnection> connections) {
        for(int j=0;j<connections.size();j++){
            for (int k=0;k<connections.size();k++){
                if(j!=k){
                    playingConnection.put(connections.get(j),connections.get(k));
                }
            }
        }
    }

    private ArrayList<PlayerExt> getPlayersforGame(Map<String, ClientConnection> waitingConnection) {
        ArrayList<PlayerExt> players= new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (int i=0; i<waitingConnection.size(); i++){
            players.add(new PlayerExt(keys.get(i)));
        }
        return players;
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        for(int i=0; i<NUMOFPOSSIBLEGAMES;i++){
            waitingConnections.add(new HashMap<>());
        }
    }

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                new Thread(socketConnection).start();
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println(room);
    }

    public void addPlayerNickName(String playerName) {
        playersNickNames.add(playerName);
    }
}
