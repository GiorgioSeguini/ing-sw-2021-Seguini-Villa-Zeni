package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.*;

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

        ArrayList<DevelopmentCard>[] developmentCards = new ArrayList[3];
        //TODO parsing
        owner.getPersonalBoard().setDevCards(developmentCards);

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        //TODO parsing
        owner.getPersonalBoard().setLeaderCards(leaderCards);
    }

    @Override
    public String getName() {
        return className;
    }
}
