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
        OwnedDevCards = new DevelopmentCard[9];
        OwnedLeaderCard = new LeaderCard[2];
    }

    //PER CARTE SVILUPPO E AZIONE NON SAREBBE MEGLIO UN ARRAYLIST?
    /**
     * 
     */
    private DevelopmentCard[] OwnedDevCards;

    /**
     * 
     */
    private LeaderCard[] OwnedLeaderCard;


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
    public int getVictoryPoints() {
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