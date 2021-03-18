package it.polimi.ingsw.model;

import java.util.*;

/**
 * Classe che gestisce la plancia del giocatore
 */
public class PersonalBoard {

    /**
     * Default constructor
     */
    public PersonalBoard() {
    }

    /**
     * 
     */
    private DevelopmentCard []OwnedDevCards = new DevelopmentCard[2];

    /**
     * 
     */
    private LeaderCard []OwnedLeaderCard = new LeaderCard[9];


    /**
     * @return
     */
    public LeaderCard[] getLeaderCards() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public ArrayList<DevelopmentCard> getTopDevCard() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public ArrayList<DevelopmentCard> getAllDevCard() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int getFaithPoints() {
        // TODO implement here
        return 0;
    }

    /**
     * throws invalid argument Exception
     * @param card 
     * @param pos 
     * @return
     */
    public void addDevCard(DevelopmentCard card, int pos) {
        // TODO implement here
    }

}