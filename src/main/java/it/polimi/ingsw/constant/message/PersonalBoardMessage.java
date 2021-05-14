package it.polimi.ingsw.constant.message;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.*;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersonalBoardMessage implements Message{

    public static final String className = "PersonalBoardMessage";
    String devCardsString;
    String leaderCardString;
    int IDplayer;

    public PersonalBoardMessage(String devCardsString, String leaderCardString, int IDplayer) {
        this.devCardsString = devCardsString;
        this.leaderCardString = leaderCardString;
        this.IDplayer = IDplayer;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(IDplayer);

        Type type = new TypeToken<ArrayList<DevelopmentCard>[]>(){}.getType();
        ArrayList<DevelopmentCard>[] developmentCards = StarterClient.fromJson(devCardsString, type);
        owner.getPersonalBoard().setDevCards(developmentCards);

        Type type1 = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
        ArrayList<LeaderCard> leaderCards = StarterClient.fromJson(leaderCardString, type1);
        owner.getPersonalBoard().setLeaderCards(leaderCards);
    }

    @Override
    public String getName() {
        return className;
    }
}
