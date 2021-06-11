package it.polimi.ingsw.server.network;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private String roomName;
    private int numOfPlayers;
    private HashMap<String,ClientConnection> connections= new HashMap<>();

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



    public String getRoomName() {
        return roomName;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public HashMap<String, ClientConnection> getConnections() {
        return connections;
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
