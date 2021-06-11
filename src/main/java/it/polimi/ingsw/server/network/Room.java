package it.polimi.ingsw.server.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {

    private String roomName;
    private int numOfPlayers;
    private HashMap<String,ClientConnection> connections= new HashMap<>();
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

    public void addConnection(String nickname, ClientConnection connection){
        connections.put(nickname, connection);
    }


    public void disconnectConnection(String nickname){
        if(connections.remove(nickname)!=null){
            disconnectedPlayers.add(nickname);
        }
        else throw new IllegalArgumentException();
    }

    public void reconnectConnection(String nickname, ClientConnection connection){
        if(disconnectedPlayers.indexOf(nickname)!=-1){
            connections.put(nickname,connection);
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
