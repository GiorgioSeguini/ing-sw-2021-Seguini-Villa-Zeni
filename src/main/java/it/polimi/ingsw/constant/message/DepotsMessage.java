package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.Depots;
import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.Player;

public class DepotsMessage implements Message{

    public static final String className = "DepotsMessage";
    private final String depots;
    private final int ownerID;

    public DepotsMessage(String depots, int ownerID) {
        this.depots = depots;
        this.ownerID = ownerID;
    }

    @Override
    public void handleMessage(Game simpleGame) {
        Player owner = simpleGame.getPlayerFromID(ownerID);
        Depots depots = new Depots();
        //TODO parsing

        owner.setDepots(depots);
    }

    @Override
    public String getName() {
        return className;
    }
}
