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
        if(client.getUI() instanceof CLI){
            System.out.println("\n\nAGGIORNAMENTO: Qualcuno si è unito alla stanza!");
            System.out.println("Giocatori attualmente collegati");
            int i=1;
            for (String name: playersName){
                System.out.println(i+". "+name);
                i++;
            }
        }else{
            // TODO: grafica della "sei nella lobby"
        }

    }

    public Set<String> getPlayersName() {
        return playersName;
    }

    @Override
    public String getName() {
        return className;
    }
}
