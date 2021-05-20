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
    private final ArrayList<DevelopmentCard>[] devCards;
    private final ArrayList<LeaderCard> leaderCards;
    int IDplayer;

    public PersonalBoardMessage( ArrayList<DevelopmentCard>[] devCards, ArrayList<LeaderCard> leaderCards, int IDplayer) {
        this.devCards = devCards;
        this.leaderCards = leaderCards;
        this.IDplayer = IDplayer;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(IDplayer);

        owner.getPersonalBoard().setDevCards(devCards);

        owner.getPersonalBoard().setLeaderCards(leaderCards);
    }

    @Override
    public String getName() {
        return className;
    }
}
