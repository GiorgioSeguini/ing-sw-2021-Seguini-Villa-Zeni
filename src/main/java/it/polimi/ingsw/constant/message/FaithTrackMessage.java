package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;

/**
 * FaithTrackMessage class.
 * Implements Message interface.
 * Manage the faith track messages.
 */
public class FaithTrackMessage implements Message{

    public static final String className = "FaithTrackMessage";
    private final FaithTrack faithTrack;
    private final int ownerID;

    /**
     * Instantiates a new Faith track message.
     *
     * @param faithTrack of type FaithTrack: the faith track.
     * @param ownerID of type int: the owner id.
     */
    public FaithTrackMessage(FaithTrack faithTrack, int ownerID) {
        this.faithTrack = faithTrack;
        this.ownerID = ownerID;
    }

    /**
     * Handle the faith track messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(ownerID);
        owner.setFaithTrack(faithTrack);
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
