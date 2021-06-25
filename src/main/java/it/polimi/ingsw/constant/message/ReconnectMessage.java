package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.cli.CLI;

import java.util.Set;

public class ReconnectMessage extends ConnectionMessage implements Message{
    
    public static final String className="ReconnectMessage";

    public ReconnectMessage(Set<String> playersName, String playerName) {
        super(playersName, playerName);
    }

    @Override
    public void handleMessage(Client client) {
        if(client.getUI()instanceof CLI){
            //CLI.setConnectionMessage(this);
            // TODO: 6/25/21 gestire sincronizzazione
        }
        else {
            // TODO: 6/25/21 gestire sincronizzazione
        }
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public String toString() {
        String x="";
        x+="\\n\\nAGGIORNAMENTO: "+super.getPlayerName()+" si è riconnesso nella stanza!\n";
        x+="Giocatori attualmente collegati\n";
        int i=1;
        for (String name: super.getPlayersName()){
            x+="\t"+i+". "+name+"\n";
            i++;
        }
        return x;
    }
}
