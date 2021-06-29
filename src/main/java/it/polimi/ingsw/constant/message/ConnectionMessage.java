package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

import java.util.Set;

/**
 * ConnectionMessage class.
 * Implements Message interface.
 * Manage the connection's messages.
 * Used when a new client is connected to the server.
 */
public class ConnectionMessage implements Message{

    public static final String className= "ConnectionAcceptedMessage";
    private final Set<String> playersName;
    private final String playerName;

    /**
     * Instantiates a new Connection message.
     *
     * @param playersName of type Set<String>: the players name.
     * @param playerName of type String: the player name.
     */
    public ConnectionMessage(Set<String> playersName, String playerName) {
        this.playersName = playersName;
        this.playerName = playerName;
    }

    /**
     * Handle the client's message.
     * Print the client's message.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
        client.getUI().printConnectionMessage(this);
    }

    /**
     * Gets players name.
     *
     * @return of type Set<String>: the players name.
     */
    public Set<String> getPlayersName() {
        return playersName;
    }

    /**
     * Gets player name.
     *
     * @return of type String: the player name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     *
     * @return of type String: the class name, useful for json serialization.
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * Get simple mex string.
     * When a new player join the room.
     * @return of type String: the string.
     */
    public String getSimpleMex(){
        return "AGGIORNAMENTO: "+playerName+ " si è unito alla stanza!";
    }

    @Override
    public String toString() {
        String x="";
        x+="AGGIORNAMENTO: "+playerName+" si è unito alla stanza!\n";
        x+="Giocatori attualmente collegati\n";
        int i=1;
        for (String name: playersName){
            x+="\t"+i+". "+name+"\n";
            i++;
        }
        return x;
    }
}
