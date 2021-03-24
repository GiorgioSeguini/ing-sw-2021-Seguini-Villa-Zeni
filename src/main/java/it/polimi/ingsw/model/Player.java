package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

import java.util.*;

public class Player {

    private String userName;
    private FaithTrack faithtrack;
    private Depots depots;
    private PersonalBoard personalBoard;

    /*Default constructor*/
    public Player() {
    }

    /*Getter*/
    public String getUserName() {
        return this.userName;
    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public Depots getDepots() {
        return this.depots;
    }

    public FaithTrack getFaithTrack() {
        return this.faithtrack;
    }

    public int getVictoryPoints(){
        // TODO implement here
        return 0;
    }

}