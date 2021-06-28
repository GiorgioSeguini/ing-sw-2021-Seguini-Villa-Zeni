package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

import java.util.Set;

public class ReconnectMessage extends ConnectionMessage implements Message{
    
    public static final String className="ReconnectMessage";

    public ReconnectMessage(Set<String> playersName, String playerName) {
        super(playersName, playerName);
    }

    @Override
    public void handleMessage(Client client) {
        client.getUI().printConnectionMessage(this);
    }

    @Override
    public String getName() {
        return className;
    }

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
