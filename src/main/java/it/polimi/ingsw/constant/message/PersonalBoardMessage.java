package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.*;

import java.util.ArrayList;

public class PersonalBoardMessage implements Message{

    String devCardsString;
    String leaderCardString;
    int IDplayer;

    public PersonalBoardMessage(String devCardsString, String leaderCardString, int IDplayer) {
        this.devCardsString = devCardsString;
        this.leaderCardString = leaderCardString;
        this.IDplayer = IDplayer;
    }

    @Override
    public void handleMessage(Game simpleGame) {
        Player owner = simpleGame.getPlayerFromID(IDplayer);

        ArrayList<DevelopmentCard>[] developmentCards = new ArrayList[3];
        //TODO parsing
        owner.getPersonalBoard().setDevCards(developmentCards);

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        //TODO parsing
        owner.getPersonalBoard().setLeaderCards(leaderCards);
    }
}