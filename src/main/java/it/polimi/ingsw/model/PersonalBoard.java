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
        return OwnedLeaderCard;
    }

    /**
     * @return
     */
    public DevelopmentCard getTopDevCard(int index) throws IllegalArgumentException {
        return OwnedDevCards[index].get(0);
    }

    public DevelopmentCard getMidDevCard(int index) throws IllegalArgumentException {
        return OwnedDevCards[index].get(1);
    }

    public DevelopmentCard getLastDevCard(int index) throws IllegalArgumentException {
        return OwnedDevCards[index].get(2);
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
    public void addDevCard(DevelopmentCard card, int pos) throws IllegalArgumentException {
        if (OwnedDevCards[pos].isEmpty()) {
            OwnedDevCards[pos].add(card);
        } else
        //if((OwnedDevCards[pos].get(0).getLevel() < card.getLevel()))           //TODO
        {
            OwnedDevCards[pos].add(0, card);
        }
    }
}