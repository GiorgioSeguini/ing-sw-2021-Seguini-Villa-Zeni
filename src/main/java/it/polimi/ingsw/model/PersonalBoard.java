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
     * Favori  papali: sono 3 e ciascuno di esso può avere 3 diversi stati
     */
    private ArrayList<PopesFavorStates>[] PopesFavor;

    /**
     * 
     */
    private bool[] PopesFavorValid;

    /**
     * 
     */
    private DevelopmentCard[] OwnedDevCards;

    /**
     * Forziere delle risorse
     */
    private ArrayList<ResourceType> StrongBox;

    /**
     * Deposito delle risorse: 3 nel primo layer, 2 nel secondo, 1 nel terzo
     */
    private PopesFavorStates[] WareHouseDepots;

    /**
     * 
     */
    private void OwnedLeaderCard[2];

    /**
     * 
     */
    public ProductionPower BasicProductionPower;

    /**
     * Intero che tiene traccia dei punti fede ottenuti
     */
    private int FaithPoints;



    /**
     * 
     */
    private Set<ProductionPower> basicProductionPower;

    /**
     * 
     */
    private DevelopmentCard OwnedDevCards[9];

    /**
     * 
     */
    private LeaderCard OwnedLeaderCard[2];




    /**
     * @param int 
     * @return
     */
    public void activateProduction(void int) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void manageDepots() {
        // TODO implement here
        return null;
    }

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
        return null;
    }

}