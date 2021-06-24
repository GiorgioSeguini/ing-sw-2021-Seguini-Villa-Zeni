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

public class Room {

    private String roomName;
    private int numOfPlayers;
    private GameExt game;
    private Controller controller;
    private HashMap<String,ClientConnection> connections= new HashMap<>();
    private HashMap<String, View> playersview= new HashMap<>();
    private ArrayList<String> disconnectedPlayers= new ArrayList<>();
    private boolean active= false;


    public Room(String roomName, int numOfPlayers) {
        this.roomName = roomName;
        this.numOfPlayers= numOfPlayers;
    }

    public Room(String roomName, int numOfPlayers, HashMap <String, ClientConnection> connections){
        this.roomName= roomName;
        this.numOfPlayers= numOfPlayers;
        for(String playerName: connections.keySet()){
            addConnection(playerName, connections.get(playerName));
        }
    }

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

    public void addConnection(String nickname, ClientConnection connection){
        connections.put(nickname, connection);
        ((SocketClientConnection)connection).setNickName(nickname);
        ((SocketClientConnection)connection).setRoom(this);
        for(String name: connections.keySet()){
            connections.get(name).send(Starter.toJson(new ConnectionMessage(connections.keySet(), nickname), Message.class));
        }
    }


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

    public void reconnectConnection(String nickname, ClientConnection connection){
        if(disconnectedPlayers.indexOf(nickname)!=-1){
            connections.put(nickname,connection);
            //RemoteView view= new RemoteView(game.getPlayerFromNickname(nickname),connection);
            playersview.get(nickname).setOffline(false);

            ((RemoteView)playersview.get(nickname)).setClientConnection(connection);
            //Server.instanceSingleView(playersview.get(nickname), game, controller);
            //Server.instanceSingleView(view,game, controller);
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

    public boolean findPlayer(String name){
        return connections.keySet().contains(name);
    }

    public boolean findDisconnectedPlayer(String name){
        return disconnectedPlayers.indexOf(name)!=-1;
    }

    public boolean isFull(){
        return connections.size()==numOfPlayers;
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
}
