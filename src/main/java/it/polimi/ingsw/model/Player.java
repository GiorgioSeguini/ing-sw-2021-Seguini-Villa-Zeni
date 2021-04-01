package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Fabio*/
public class Player {

    private String userName;
    private FaithTrack faithtrack;
    private Depots depots;
    private PersonalBoard personalBoard;
    private Converter converter;
    int victorypoints;

    /*Default constructor*/
    public Player() {
        victorypoints=0;
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

    public Converter getConverter(){
        return this.converter;
    }

    public int getVictoryPoints(){
        victorypoints = personalBoard.getVictoryPoints() + faithtrack.getVictoryPoints() + depots.getVictoryPoints();
        return victorypoints;
    }
    public void ActivateLeaderCard(int index) throws IllegalArgumentException{
        LeaderCard toactivate= getPersonalBoard().getLeaderCards()[index];
        toactivate.setPlayed(this);
    }

}