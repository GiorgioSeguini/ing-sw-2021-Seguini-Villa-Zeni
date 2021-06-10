package it.polimi.ingsw.server.network;

import java.util.HashMap;

public class Room {

    private String roomName;
    private int numOfPlayers;
    private HashMap<String,ClientConnection> connections= new HashMap<>();

    public Room(String roomName, int numOfPlayers) {
        this.roomName = roomName;
        this.numOfPlayers= numOfPlayers;
    }

    public void addConnection(String nickname, ClientConnection connection){
        connections.put(nickname, connection);
    }

    public String getRoomName() {
        return roomName;
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
