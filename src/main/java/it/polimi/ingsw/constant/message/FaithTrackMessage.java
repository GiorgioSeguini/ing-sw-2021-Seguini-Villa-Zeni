package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;
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
