package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

import java.util.Set;

/**
 * ReconnectMessage class.
 * Implements Message interface.
 * Manage the reconnecting messages.
 */
public class ReconnectMessage extends ConnectionMessage implements Message{

    public static final String className="ReconnectMessage";

    /**
     * Instantiates a new Reconnect message.
     *
     * @param playersName of type Set<String>: the players name.
     * @param playerName of type String: the player's name.
     */
    public ReconnectMessage(Set<String> playersName, String playerName) {
        super(playersName, playerName);
    }

    /**
     * Handle and print the reconnecting messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
        client.getUI().printConnectionMessage(this);
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
     * Get the message that has to be sent to others players when one of them is reconnecting to the game.
     * @return of type String: the message.
     */
    @Override
    public String getSimpleMex(){
        return "AGGIORNAMENTO: "+super.getPlayerName()+ " si è riconnesso alla stanza!";
    }

    @Override
    public String toString() {
        String x="";
        x+="AGGIORNAMENTO: "+super.getPlayerName()+" si è riconnesso nella stanza!\n";
        x+="Giocatori attualmente collegati\n";
        int i=1;
        for (String name: super.getPlayersName()){
            x+="\t"+i+". "+name+"\n";
            i++;
        }
        return x;
    }
}
