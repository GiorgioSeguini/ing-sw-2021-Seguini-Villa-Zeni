package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.UnableToFillError;

import java.util.*;

/*Last Edit: Fabio*/
public class Player {

    private final String userName;
    private final FaithTrack faithtrack;
    private final Depots depots;
    private final PersonalBoard personalBoard;
    private final Converter converter;
    //private final ArrayList<ResourceType> discounted;         //or Buyer class
    private int victoryPoints;

    /*Default constructor*/
    public Player(String userName, int initialFaithPoints, NumberOfResources initialResources) {
        this.userName=userName;
        faithtrack = new FaithTrack(initialFaithPoints);
        depots = new Depots();
        try {
            depots.addResourcesFromMarket(initialResources);
        }catch(UnableToFillError ignored){}
        personalBoard = new PersonalBoard();
        converter = new Converter(this);
        victoryPoints=0;
        //discounted = new ArrayList<ResourceType>();
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
        victoryPoints = personalBoard.getVictoryPoints() + faithtrack.getVictoryPoints() + depots.getVictoryPoints();
        return victoryPoints;
    }
    public void ActivateLeaderCard(int index) throws IllegalArgumentException{
        LeaderCard toActivate= getPersonalBoard().getLeaderCards()[index];
        toActivate.setPlayed(this);
    }

    /*
    public void addDiscount(ResourceType type){
        discounted.add(type);
    }

    public boolean getDiscount(ResourceType type){

    }*/
}