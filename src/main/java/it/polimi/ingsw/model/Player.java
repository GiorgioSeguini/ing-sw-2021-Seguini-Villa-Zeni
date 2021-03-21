package it.polimi.ingsw.model;

import java.util.*;

/**
 * 
 */
public class Player {

    /**
     * Default constructor
     */
    public Player() {
    }

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private PersonalBoard personalBoard;

    /**
     * 
     */
    private Depots depots;

    /**
     * 
     */
    private FaithTrack faithtrack;

    /**
     * @return
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @return
     */
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    /**
     * @return
     */
    public Depots getDepots() {
        return this.depots;
    }

    /**
     * @return
     */
    public FaithTrack getFaithTrack() {
        return this.faithtrack;
    }

    /**
     *
     */
    public int getVictoryPoints(){
        // TODO implement here
        return 0;
    }

}