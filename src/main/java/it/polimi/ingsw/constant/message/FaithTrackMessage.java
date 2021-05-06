package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.FaithTrack;
import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.Player;

public class FaithTrackMessage implements Message{

    public static final String className = "FaithTrackMessage";
    private final String faithTrack;
    private final int ownerID;

    public FaithTrackMessage(String faithTrack, int ownerID) {
        this.faithTrack = faithTrack;
        this.ownerID = ownerID;
    }

    @Override
    public void handleMessage(Game simpleGame) {
        Player owner = simpleGame.getPlayerFromID(ownerID);
        FaithTrack faithTrack = new FaithTrack();

        owner.setFaithTrack(faithTrack);
    }

    @Override
    public String getName() {
        return className;
    }
}
