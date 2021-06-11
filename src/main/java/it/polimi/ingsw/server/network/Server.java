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
    private final ArrayList<HashMap<String, ClientConnection>> waitingConnections = new ArrayList<>();
    //private final Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private ArrayList<Room> rooms= new ArrayList<>();
    private ArrayList<Room> activeRooms= new ArrayList<>();
    private int id;

    //Deregister connection
    public synchronized void deregisterConnection() {
        // TODO: 6/11/21

    }

    /**Returns true if it finds the name in the specified waitinglist (form 1 to 4)*/
    public synchronized boolean findName(String playerName, int listIndex){
        if(listIndex<5 && listIndex>0)
            return waitingConnections.get(listIndex-1).containsKey(playerName);
        return false;
    }

    /**Return true if it finds the room in the list of rooms or active rooms*/
    public synchronized boolean findRoom(String name){
        for(Room x: rooms ){
            if(x.getRoomName().equals(name)){
                return true;
            }
        }
        for(Room x: activeRooms){
            if(x.getRoomName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public synchronized boolean findActiveRoom(String name){
        for(Room x: activeRooms){
            if(x.getRoomName().equals(name)){
                return true;
            }
        }
        return false;
    }

    //Wait for another player
    public synchronized boolean lobby(ClientConnection c, String name, int numofplayer){

        if(numofplayer<1 || numofplayer>4){
            throw  new IllegalArgumentException();
        }
        waitingConnections.get(numofplayer-1).put(name,c);

        if (waitingConnections.get(numofplayer-1).size()>=numofplayer) {
            String roomName;
            do {
                roomName="Room"+id;
                id++;
            }while (findRoom(roomName));
            Room room= new Room(roomName,numofplayer,waitingConnections.get(numofplayer-1));
            startGame(room);
            addActiveRoom(room);
            waitingConnections.get(numofplayer-1).clear();
        }
        return true;
    }

    public void startGame(Room room){
        ArrayList<PlayerExt> players=getPlayersforGame(room.getConnections());
        GameExt game = null;

        try {
            game=new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), Starter.TokensParser(), Starter.LeaderCardsParser());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //TODO
        }
        Controller controller = new Controller(game);
        HashMap<String,View> playersView = instanceViews(room.getConnections(),game.getPlayers());

        addObserverGame(new ArrayList<>(playersView.values()), game, controller);

        //ArrayList<ClientConnection> connections= getConnectionforGame(room.getConnections(), game.getPlayers());
        //makeConnection(connections);

        //send initial message
        for(View view : playersView.values()) {
            view.sendInitialMessage(game, room.getRoomName());
        }

        room.setGame(game);
        room.setController(controller);
    }

    private void addObserverGame(ArrayList<View> playersView, GameExt game, Controller controller) {
        for(View view : playersView){
            //add model - view links
            instanceSingleView(view, game, controller);
        }
    }

    public static void instanceSingleView(View view, GameExt game, Controller controller){
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

    private HashMap<String,View> instanceViews(Map<String, ClientConnection> waitingConnection, ArrayList<Player> players) {
        HashMap <String, View> playersView =new HashMap<>();
        for(Player player: players){
            playersView.put(player.getUserName(), new RemoteView(player,waitingConnection.get(player.getUserName())));
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
                    //playingConnection.put(connections.get(j),connections.get(k));
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

    public void addActiveRoom(Room room){
        activeRooms.add(room);
    }

    public void removeRoom(Room room){
        rooms.remove(room);
    }

    public void removeActiveRooms(Room room){
        activeRooms.remove(room);
    }


    public synchronized Room getRoomFromName(String roomName){
        for(Room room: rooms){
            if(room.getRoomName().equals(roomName)){
                return room;
            }
        }
        for(Room room: activeRooms){
            if(room.getRoomName().equals(roomName)){
                return room;
            }
        }
        return null;
    }

}
