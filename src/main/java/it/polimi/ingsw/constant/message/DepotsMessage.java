package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Depots;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;

/**
 * DepotsMessage class.
 * Implements Message interface.
 * Manage the depot's messages.
 */
public class DepotsMessage implements Message{

    public static final String className = "DepotsMessage";
    private final Depots depots;
    private final int ownerID;

    /**
     * Instantiates a new Depots message.
     *
     * @param depots of type Depots: the depots.
     * @param ownerID of type int: the player's id.
     */
    public DepotsMessage(Depots depots, int ownerID) {
        this.depots = depots;
        this.ownerID = ownerID;
    }

    /**
     * Handle depot's messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(ownerID);
        owner.setDepots(depots);
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
