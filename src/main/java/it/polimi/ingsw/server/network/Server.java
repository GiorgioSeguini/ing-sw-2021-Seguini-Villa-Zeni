package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.DashboardExt;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.MarketExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.server.view.RemoteView;
import it.polimi.ingsw.server.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Server class. This class menages all the aspectes for connections, disconnections, reconnections, and creating games.
 */
@SuppressWarnings("ALL")
public class Server implements Runnable{

    private static final int PORT = 12345;
    private static final int NUMOFPOSSIBLEGAMES= 4;
    private final ServerSocket serverSocket;
    private final ArrayList<Room>  defaultRooms= new ArrayList<>();
    private final ArrayList<Room> rooms= new ArrayList<>();
    private final ArrayList<Room> activeRooms= new ArrayList<>();
    private int id=1;

    /**
     * Default constructor.
     * @throws IOException
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        for(int i=1; i<=NUMOFPOSSIBLEGAMES;i++){
            defaultRooms.add(new Room("defaultRoom"+i,i));
        }
    }


    /**
     * Method that finds a player in a DefaultRoom for public game
     * @param playerName
     * @param listIndex
     * @return true if it finds the name in the specified defaultRoom (form 1 to 4)
     */
    public synchronized boolean findName(String playerName, int listIndex){
        if(listIndex<5 && listIndex>0)
            return defaultRooms.get(listIndex-1).findPlayer(playerName);
        throw new IllegalArgumentException();
    }


    /**
     * Tries to find the room by its name in all room's list.
     * @param name
     * @return true if it finds the room in the list of rooms or active rooms
     */
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

        for(Room x: defaultRooms){
            if(x.getRoomName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     *Tries to find the room by its name in the active room list.
     * @param name
     * @return true if it finds the room in the active list
     */
    public synchronized boolean findActiveRoom(String name){
        for(Room x: activeRooms){
            if(x.getRoomName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     *  Methods that menage the connection of a public game. It puts a player into the specified default room.
     *  If the room is full creates a new room with a never used name and copys the content in the new Room,
     *  then starts the game.
     * @param c
     * @param name
     * @param numofplayer
     * @throws IllegalArgumentException if number of players one wants to play with is not in the range 1-4
     */
    public synchronized void lobby(ClientConnection c, String name, int numofplayer){

        if(numofplayer<1 || numofplayer>4){
            throw  new IllegalArgumentException();
        }

        defaultRooms.get(numofplayer-1).addConnection(name,c);

        if(defaultRooms.get(numofplayer-1).isFull()){
            String roomName;
            do {
                roomName="Room"+id;
                id++;
            }while (findRoom(roomName));
            Room room= new Room(roomName,numofplayer,defaultRooms.get(numofplayer-1).getConnections());
            startGame(room);
            room.setActive();
            addActiveRoom(room);
            defaultRooms.get(numofplayer-1).clear();
        }
    }

    /**
     * Methods that instances the game with its network structure. Gets the players from the room
     * and creates their Views. Then populate the game, create a controller, adds the oberservers
     * and sends the initial message.
     * @param room
     */
    public void startGame(Room room){
        ArrayList<PlayerExt> players=getPlayersforGame(room.getConnections());
        GameExt game = null;

        game=new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), Starter.TokensParser(), Starter.LeaderCardsParser());
        Controller controller = new Controller(game);
        HashMap<String,View> playersView = instanceViews(room.getConnections(),game.getPlayers());

        addObserverGame(new ArrayList<>(playersView.values()), game, controller);

        //send initial message
        for(View view : playersView.values()) {
            view.sendInitialMessage(game, room.getRoomName());
        }

        room.setGame(game);
        room.setController(controller);
        room.setPlayersview(playersView);
    }

    /**
     * Makes the game obervable from all the Views.
     * @param playersView
     * @param game
     * @param controller
     */
    private void addObserverGame(ArrayList<View> playersView, GameExt game, Controller controller) {
        for(View view : playersView){
            //add model - view links
            instanceSingleView(view, game, controller);
        }
    }

    /**
     * Makes Market, Dashboard, Game, observable from the View and makes th View observable from Controller.
     * @param view
     * @param game
     * @param controller
     */
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

    /**
     * Creates a View for all the players.
     * @param waitingConnection
     * @param players
     * @return an Hashmap<String, View> with keys the players and values their View
     */
    private HashMap<String,View> instanceViews(Map<String, ClientConnection> waitingConnection, ArrayList<Player> players) {
        HashMap <String, View> playersView =new HashMap<>();
        for(Player player: players){
            playersView.put(player.getUserName(), new RemoteView(player,waitingConnection.get(player.getUserName())));
        }
        return playersView;
    }

    /**
     * Takes the nickaname and creates its player for all the players in the game.
     * @param waitingConnection
     * @return an ArrayList<PlayerExt>
     */
    private ArrayList<PlayerExt> getPlayersforGame(Map<String, ClientConnection> waitingConnection) {
        ArrayList<PlayerExt> players= new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (int i=0; i<waitingConnection.size(); i++){
            players.add(new PlayerExt(keys.get(i)));
        }
        return players;
    }

    /**
     * Methods that keeps listening for a new Connection.
     * If a connection is taken creates a new Thread for it.
     */
    @Override
    public void run(){
        System.out.println("Server is running...");
        System.out.println("Server socket info:");
        System.out.println("\t port: "+serverSocket.getLocalPort());
        System.out.println("\t address: "+serverSocket.getInetAddress());
        System.out.println("\nWaiting for messages..\n");
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

    /**
     * Simply adds a Room to the room list.
     * @param room
     */
    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println(room);
    }

    /**
     * Simply adds a Room to the active room list.
     * @param room
     */
    public void addActiveRoom(Room room){
        activeRooms.add(room);
    }
    /**
     * Simply removes a Room from all room list.
     * @param room
     */
    public void removeRoom(Room room){
        if(findActiveRoom(room.getRoomName())){
            activeRooms.remove(room);
        }
        else{
            rooms.remove(room);
        }
    }

    /**
     * Find a room in all the room list by its name.
     * @param roomName
     * @return a Room if can find it or null if it doesn't.
     */
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
