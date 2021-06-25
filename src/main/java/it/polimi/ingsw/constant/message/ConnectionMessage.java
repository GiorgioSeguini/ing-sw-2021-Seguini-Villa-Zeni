package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.CLI;

import java.util.Set;

public class ConnectionMessage implements Message{

    public static final String className= "ConnectionAcceptedMessage";
    private final Set<String> playersName;
    private final String playerName;

    public ConnectionMessage(Set<String> playersName, String playerName) {
        this.playersName = playersName;
        this.playerName = playerName;
    }

    @Override
    public void handleMessage(Client client) {
        client.getUI().printConnectionMessage(this);
    }

    public Set<String> getPlayersName() {
        return playersName;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String getName() {
        return className;
    }

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
