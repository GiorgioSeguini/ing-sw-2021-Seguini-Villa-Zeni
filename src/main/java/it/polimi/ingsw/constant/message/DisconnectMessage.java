package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

import java.util.Set;

/**
 * DisconnectMessage class.
 * Implements Message interface.
 * Manage the disconnecting messages.
 */
public class DisconnectMessage extends ConnectionMessage implements Message{

    public static final String className="DisconnectMessage";

    /**
     * Instantiates a new Disconnect message.
     *
     * @param playersName of type Set<String>: the name of the players.
     * @param playerName of type String: the player's name.
     */
    public DisconnectMessage(Set<String> playersName, String playerName) {
        super(playersName, playerName);
    }

    /**
     * Handle and print the disconnecting messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
        client.getUI().printConnectionMessage(this);
    }

    /**
     * Get the message that has to be sent to others players when one of them leave the game.
     * @return of type String: the message.
     */
    @Override
    public String getSimpleMex(){
        return "AGGIORNAMENTO: "+super.getPlayerName()+ " si è disconnesso dalla stanza!";
    }

    @Override
    public String toString() {
        String x="";
        x+="AGGIORNAMENTO: "+super.getPlayerName()+" si è disconnesso dalla stanza!\n";
        x+="Giocatori attualmente collegati\n";
        int i=1;
        for (String name: super.getPlayersName()){
            x+="\t"+i+". "+name+"\n";
            i++;
        }
        return x;
    }

    /**
     *
     * @return of type String: the class name, useful for json serialization.
     */
    @Override
    public String getName() {
        return className;
    }
}
