package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.message.ConnectionMessage;
import it.polimi.ingsw.constant.message.DisconnectMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.ReconnectMessage;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.server.view.RemoteView;
import it.polimi.ingsw.server.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a room where the player can join as a wainting space.
 * It contains the nicknames of the players in the game and their connections and View.
 */
public class Room {

    private final String roomName; /**roomName is the identifier of a room. It must be different from all rooms.*/
    private final int numOfPlayers;/**Number of player that the room will host. Max 4 */
    private GameExt game;
    private Controller controller;
    private final HashMap<String,ClientConnection> connections= new HashMap<>();
    private HashMap<String, View> playersview= new HashMap<>();
    private final ArrayList<String> disconnectedPlayers= new ArrayList<>();
    private boolean active= false;


    /**
     * Default constructor.
     * @param roomName
     * @param numOfPlayers
     */
    public Room(String roomName, int numOfPlayers) {
        if(numOfPlayers<0 || numOfPlayers>4){
            throw new IllegalArgumentException();
        }

        this.roomName = roomName;
        this.numOfPlayers= numOfPlayers;
    }

    /**
     * Super constructor that directly set the connections.
     * @param roomName
     * @param numOfPlayers
     * @param connections
     */
    public Room(String roomName, int numOfPlayers, HashMap <String, ClientConnection> connections){
        if(numOfPlayers<0 || numOfPlayers>4){
            throw new IllegalArgumentException();
        }
        this.roomName= roomName;
        this.numOfPlayers= numOfPlayers;
        for(String playerName: connections.keySet()){
            addConnectionWithoutMessage(playerName, connections.get(playerName));
        }
    }

    /**
     * @return true if the game is already started. In that case a room is active.
     */
    public boolean isActive(){
        return active;
    }

    public void setActive(){
        active=true;
    }

    public void setPlayersview(HashMap<String, View> playersview) {
        this.playersview = playersview;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setGame(GameExt game) {
        this.game = game;
    }

    public GameExt getGame() {
        return game;
    }

    /**
     * Add a single connection to the room. When a connection is stored it sends a ConnectionMessage to all the players in the room.
     * @param nickname
     * @param connection
     */
    public void addConnection(String nickname, ClientConnection connection){
        connections.put(nickname, connection);
        ((SocketClientConnection)connection).setNickName(nickname);
        ((SocketClientConnection)connection).setRoom(this);
        for(String name: connections.keySet()){
            connections.get(name).send(Starter.toJson(new ConnectionMessage(connections.keySet(), nickname), Message.class));
        }
    }

    /**
     * Add a single connection to the room without notify the players.
     * @param nickname
     * @param connection
     */
    private void addConnectionWithoutMessage(String nickname, ClientConnection connection){
        connections.put(nickname, connection);
        ((SocketClientConnection)connection).setNickName(nickname);
        ((SocketClientConnection)connection).setRoom(this);
    }


    /**
     * Removes a player from the active connection and stores it to disconnected players list and sets her View Offline.
     * If theres no more active players it clears the room.
     * It notify all the players about the disconnection.
     * @param nickname
     * @return true if theres no more active players in the room, false otherwise.
     * @throws IllegalArgumentException if it doesnt find the player in the connection list.
     */
    public boolean disconnectConnection(String nickname){
        if(connections.remove(nickname)!=null){
            if(isActive()){
                disconnectedPlayers.add(nickname);
            }
            if (connections.size()==0){
                clear();
                return true;
            }
            for(String name: connections.keySet()){
                connections.get(name).send(Starter.toJson(new DisconnectMessage(connections.keySet(), nickname), Message.class));
            }
            try{
                playersview.get(nickname).setOffline(true);//if the connections size is 0 here enters an infinite loop because of autoplay
            }catch (NullPointerException ignored){/*when the match isn't started yet I dont have any views*/}

        }
        else throw new IllegalArgumentException();

        return false;
    }

    /**
     * Reconnect a player to a room, setting the new SocketClientConnection and settings her View to ON.
     * Notify all the player about the reconnections.
     * @param nickname
     * @param connection
     * @throws IllegalArgumentException if the player is not in the disconnect player list.
     */
    public void reconnectConnection(String nickname, ClientConnection connection){
        if(disconnectedPlayers.contains(nickname)){
            connections.put(nickname,connection);
            playersview.get(nickname).setOffline(false);

            ((RemoteView)playersview.get(nickname)).setClientConnection(connection);
            playersview.get(nickname).sendInitialMessage(game, getRoomName());
            for(String name: connections.keySet()){
                connections.get(name).send(Starter.toJson(new ReconnectMessage(connections.keySet(), nickname), Message.class));
            }
            disconnectedPlayers.remove(nickname);
        }
        else throw new IllegalArgumentException();
    }

    public String getRoomName() {
        return roomName;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public HashMap<String, ClientConnection> getConnections() {
        return connections;
    }

    /**
     * @param name
     * @return true if the player name is found in the active connections list.
     */
    public boolean findPlayer(String name){
        return connections.keySet().contains(name);
    }

    /**
     * @param name
     * @return true if the player name is found in the disconnected connections list.
     */
    public boolean findDisconnectedPlayer(String name){
        return disconnectedPlayers.contains(name);
    }


    /**
     * @return true if the number of connections matches the number of player that the room was waiting.
     */
    public boolean isFull(){
        return connections.size()==numOfPlayers;
    }

    /**
     * Methods that takes advantage on default java garbage collector. It sets the game and controller to null, clears the connection player list
     * and set the room to not active.
     */
    public void clear() {
        if(game!=null){
            game.close();
            game=null;
            controller=null;
        }
        connections.clear();
        playersview.clear();
        disconnectedPlayers.clear();
        active=false;
    }

    @Override
    public String toString() {
        String x="";
        x+="Room "+ roomName +"  ("+numOfPlayers+" giocatori)\n";
        x+="Giocatori :";
        for (String name: connections.keySet()){
            x+="\t"+name;
        }

        return x;
    }

}
