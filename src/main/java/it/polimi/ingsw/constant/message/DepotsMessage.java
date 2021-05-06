package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.Depots;
import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.Player;
import it.polimi.ingsw.client.parser.StarterClient;

public class DepotsMessage implements Message{

    public static final String className = "DepotsMessage";
    private final String depots;
    private final int ownerID;

    public DepotsMessage(String depots, int ownerID) {
        this.depots = depots;
        this.ownerID = ownerID;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(ownerID);
        owner.setDepots(StarterClient.fromJsonDepots(depots));
    }

    @Override
    public String getName() {
        return className;
    }
}
