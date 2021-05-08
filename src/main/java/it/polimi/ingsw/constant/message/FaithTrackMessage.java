package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.FaithTrack;
import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.Player;
import it.polimi.ingsw.client.parser.StarterClient;

public class FaithTrackMessage implements Message{

    public static final String className = "FaithTrackMessage";
    private final String faithTrack;
    private final int ownerID;

    public FaithTrackMessage(String faithTrack, int ownerID) {
        this.faithTrack = faithTrack;
        this.ownerID = ownerID;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(ownerID);
        owner.setFaithTrack(StarterClient.fromJson(faithTrack, FaithTrack.class));
    }

    @Override
    public String getName() {
        return className;
    }
}
