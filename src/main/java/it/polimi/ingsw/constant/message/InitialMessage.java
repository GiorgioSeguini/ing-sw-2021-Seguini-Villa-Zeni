package it.polimi.ingsw.constant.message;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.client.parser.StarterClient;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InitialMessage implements Message{

    public static final String className = "InitialMessage";

    private final Game model;
    private final int yourID;
    private final ArrayList<LeaderCard> leaderCards;

    public InitialMessage(Game model, int yourID, ArrayList<LeaderCard> leaderCards) {
        this.model = model;
        this.yourID = yourID;
        this.leaderCards = leaderCards;
    }

    @Override
    public void handleMessage(Client client){
        client.setSimpleGame((GameClient) model);
        GameClient simpleGame = client.getSimpleGame();
        simpleGame.setMyID(yourID);

        simpleGame.setLeaderCards(leaderCards);
    }

    @Override
    public String getName() {
        return className;
    }
}
