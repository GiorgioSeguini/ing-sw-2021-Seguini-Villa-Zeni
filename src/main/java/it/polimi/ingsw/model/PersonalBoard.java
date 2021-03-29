package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Fabio*/
public class PersonalBoard {

    private ArrayList<DevelopmentCard>[] OwnedDevCards;

    private LeaderCard[] OwnedLeaderCard;

    /*Default Constructor*/
    public PersonalBoard() {
        OwnedDevCards = new ArrayList[3]; //array di arraylist
        OwnedLeaderCard = new LeaderCard[2];
    }

    /*Getter*/
    public LeaderCard[] getLeaderCards() {
        return OwnedLeaderCard.clone();
    }

    /**Those methods allow to get top DevCard of the stacks on the player's personal board**/
    public DevelopmentCard getTopDevCard(int index) {
        DevelopmentCard topdevcard = null;
        if(goodindex(index)){
            topdevcard = OwnedDevCards[index].get(0);
        }
        return topdevcard;
    }

    /**
    public DevelopmentCard getMidDevCard(int index) {
        DevelopmentCard midevcard = null;
        if(goodindex(index)){
            midevcard = OwnedDevCards[index].get(1);
        }
        return midevcard;
    }

    public DevelopmentCard getLastDevCard(int index) {
        DevelopmentCard lastdevcard = null;
        if(goodindex(index)){
        lastdevcard = OwnedDevCards[index].get(2);
        }
        return lastdevcard;
    }**/

    /**This allow to get all DevCard on the player's personal board**/
    public ArrayList<DevelopmentCard> getAllDevCard() {
        ArrayList<DevelopmentCard> AllDevCard = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            AllDevCard.addAll((Collection<? extends DevelopmentCard>) OwnedDevCards[i].clone());    //DA RIVEDERE
        }
        return AllDevCard;
    }

    /**This return the sum of card's victory points**/
    public int getVictoryPoints() {
        int victorypoints = 0;
        for (DevelopmentCard developmentCard : getAllDevCard()) {
            victorypoints += developmentCard.getVictoryPoints();
        }
        for (LeaderCard leaderCard : getLeaderCards()) {
            victorypoints += leaderCard.getVictoryPoints();
        }
        return victorypoints;
    }

    /**This for add a DevCard in a specific position**/
    public void addDevCard(DevelopmentCard card, int pos) {
        if(goodindex(pos)){
        if (OwnedDevCards[pos].isEmpty()) {
            OwnedDevCards[pos].add(card);
        } else
        if(OwnedDevCards[pos].get(0).getLevel().ordinal() < card.getLevel().ordinal())
        {
            OwnedDevCards[pos].add(0, card);
        }
        }
    }

    /**This for check the index**/
    private boolean goodindex(int index) throws IllegalArgumentException{
        if(index>2 || index<0) {
            throw new IllegalArgumentException();
        }
        return true;
    }
}