package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.CLI;

import java.util.ArrayList;
import java.util.Set;

public class ConnectionMessage implements Message{

    public static final String className= "ConnectionAcceptedMessage";
    private boolean isDisconnection=false;
    private final Set<String> playerName;

    public ConnectionMessage(Set<String> playerName) {
        this.playerName = playerName;
    }

    @Override
    public void handleMessage(Client client) {
        if(client.getUI() instanceof CLI){
            System.out.println("\n\nAGGIORNAMENTO: Qualcuno si è unito alla stanza!");
            System.out.println("Giocatori attualmente collegati");
            int i=1;
            for (String name: playerName){
                System.out.println(i+". "+name);
                i++;
            }
        }else{
            // TODO: 6/24/21 alertBox
        }

    }

    public void setDisconnectionMex(){
        this.isDisconnection=true;
    }

    public boolean isDisconnection() {
        return isDisconnection;
    }

    @Override
    public String getName() {
        return className;
    }
}
