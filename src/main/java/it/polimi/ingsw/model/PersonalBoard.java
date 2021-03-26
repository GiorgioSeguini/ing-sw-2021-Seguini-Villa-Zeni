package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Fabio*/

public class PersonalBoard {

    private ArrayList<DevelopmentCard>[] OwnedDevCards;

    private LeaderCard[] OwnedLeaderCard;

    //default constructor
    public PersonalBoard() {
        OwnedDevCards = new ArrayList[3]; //array di arraylist
        OwnedLeaderCard = new LeaderCard[2];
    }

    /**
     * @return
     */
    public LeaderCard[] getLeaderCards() {
        return OwnedLeaderCard.clone();
    }

    /**
     * @return
     */
    public DevelopmentCard getTopDevCard(int index) {
        DevelopmentCard topdevcard = null;
        if(goodindex(index)){
            topdevcard = OwnedDevCards[index].get(0);
        }
        return topdevcard;
    }

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
    }

    /**
     * @return
     */
    public ArrayList<DevelopmentCard> getAllDevCard() {
        ArrayList<DevelopmentCard> AllDevCard = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            AllDevCard.addAll((Collection<? extends DevelopmentCard>) OwnedDevCards[i].clone());    //DA RIVEDERE
        }
        return AllDevCard;
    }

    /**
     * @return
     */
    public int getVictoryPoints() {
        int victorypoints = 0;
        for (DevelopmentCard developmentCard : getAllDevCard()) {
            victorypoints += developmentCard.getVictoryPoints();
        }
        for (LeaderCard leaderCard : getLeaderCards()) {
            victorypoints *= leaderCard.getVictoryPoints();
        }
        return victorypoints;
    }

    /**
     * throws invalid argument Exception
     *
     * @param card
     * @param pos
     * @return
     */
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

    private boolean goodindex(int index) throws IllegalArgumentException{
        if(index>2 || index<0) {
            throw new IllegalArgumentException();
        }
        return true;
    }
}