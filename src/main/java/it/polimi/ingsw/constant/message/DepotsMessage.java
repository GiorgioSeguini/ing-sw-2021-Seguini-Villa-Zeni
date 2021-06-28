package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Depots;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;

public class DepotsMessage implements Message{

    public static final String className = "DepotsMessage";
    private final Depots depots;
    private final int ownerID;

    public DepotsMessage(Depots depots, int ownerID) {
        this.depots = depots;
        this.ownerID = ownerID;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(ownerID);
        owner.setDepots(depots);
    }

    @Override
    public String getName() {
        return className;
    }
}
