package it.polimi.ingsw.model;

import java.util.*;

/**
 * 
 */
public class FaithTrack {

    /**
     * Default constructor
     */
    public FaithTrack() {
    }

    /**
     * 
     */
    private int faithPoints;

    /**
     *
     */
    private static ArrayList<Integer> victoryPoints;

    /**
     * 
     */
    private ArrayList<PopesFavorStates> PopesFavor;

    /**
     * @return
     */
    public int getFaithPoints() {
        return faithPoints;
    }

    /**
     * @return
     */
    public int getVictoryPoints() {
        // TODO implement here
        return 0;
    }

    /**
     * return true if you need to activate Pope's Inspection
     * @return
     */
    public boolean addPoint() {
        // TODO implement here
        return false;
    }

}