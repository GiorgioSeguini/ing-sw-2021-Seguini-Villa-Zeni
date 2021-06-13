package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.view.RemoteView;
import it.polimi.ingsw.server.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {

    private String roomName;
    private int numOfPlayers;
    private GameExt game;
    private Controller controller;
    private HashMap<String,ClientConnection> connections= new HashMap<>();
    private HashMap<String, View> playersview= new HashMap<>();
    private ArrayList<String> disconnectedPlayers= new ArrayList<>();


    public Room(String roomName, int numOfPlayers) {
        this.roomName = roomName;
        this.numOfPlayers= numOfPlayers;
    }

    public Room(String roomName, int numOfPlayers, HashMap <String, ClientConnection> connections){
        this.roomName= roomName;
        this.numOfPlayers= numOfPlayers;
        this.connections=connections;
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

    public void addConnection(String nickname, ClientConnection connection){
        connections.put(nickname, connection);
        ((SocketClientConnection)connection).setNickName(nickname);
        ((SocketClientConnection)connection).setRoom(this);
    }


    public void  disconnectConnection(String nickname){
        if(connections.remove(nickname)!=null){
            disconnectedPlayers.add(nickname);
            playersview.get(nickname).setOffline(true);
        }
        else throw new IllegalArgumentException();
    }

    public void reconnectConnection(String nickname, ClientConnection connection){
        if(disconnectedPlayers.indexOf(nickname)!=-1){
            connections.put(nickname,connection);
            //RemoteView view= new RemoteView(game.getPlayerFromNickname(nickname),connection);
            playersview.get(nickname).setOffline(false);

            ((RemoteView)playersview.get(nickname)).setClientConnection(connection);
            //Server.instanceSingleView(playersview.get(nickname), game, controller);
            //Server.instanceSingleView(view,game, controller);
            playersview.get(nickname).sendInitialMessage(game, getRoomName());
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

    public boolean findPlayer(String name){
        return connections.keySet().contains(name);
    }

    public boolean findDisconnectedPlayer(String name){
        return disconnectedPlayers.indexOf(name)!=-1;
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
