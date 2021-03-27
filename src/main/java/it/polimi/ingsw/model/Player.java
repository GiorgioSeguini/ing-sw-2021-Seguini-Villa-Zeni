package it.polimi.ingsw.model;

/*Last Edit: Fabio*/

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
        int victorypoints=0;
        victorypoints= personalBoard.getVictoryPoints() + faithtrack.getVictoryPoints();  //TODO bisogna aggiungere anche i points del depots
        return victorypoints;
    }
    public void ActivateLeaderCard(int index) throws IllegalArgumentException{
        LeaderCard toactivate= getPersonalBoard().getLeaderCards()[index];
        toactivate.setPlayed(this);
    }

}