package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.DevelopmentCard;
import it.polimi.ingsw.client.modelClient.Game;

import java.util.ArrayList;

public class DashBoardMessage implements Message{

    ArrayList<String> developmentCardsString;

    public DashBoardMessage(ArrayList<String> cards){
        this.developmentCardsString = new ArrayList<>();
        this.developmentCardsString.addAll(cards);
    }

    @Override
    public void handleMessage(Game simpleGame){
        //TODO parsing
        ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();
        simpleGame.getDashboard().setCards(developmentCards);
    }
}
