package it.polimi.ingsw.constant.message;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.client.parser.StarterClient;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InitialMessage implements Message{

    public static final String className = "InitialMessage";

    private final String model;
    private final int yourID;
    private final String leaderCardsString;

    public InitialMessage(String model, int yourID, String leaderCards) {
        this.model = model;
        this.yourID = yourID;
        this.leaderCardsString = leaderCards;
    }

    @Override
    public void handleMessage(Client client){
        client.setSimpleGame(StarterClient.fromJson(model, GameClient.class));
        GameClient simpleGame = client.getSimpleGame();
        simpleGame.setMyID(yourID);

        Type type = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
        ArrayList<LeaderCard> leaderCards = StarterClient.fromJson(leaderCardsString, type);
        simpleGame.setLeaderCards(leaderCards);
    }

    @Override
    public String getName() {
        return className;
    }
}
