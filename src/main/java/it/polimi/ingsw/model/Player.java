package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.model.exception.UnableToFillException;

/*Last Edit: Fabio*/
public class Player {

    private final String userName;
    private final FaithTrack faithtrack;
    private final Depots depots;
    private PersonalBoard personalBoard;
    private final Converter converter;
    private NumberOfResources discounted;
    private int victoryPoints;

    /*Default constructor*/
    public Player(String userName, int initialFaithPoints, NumberOfResources initialResources) {
        this.userName=userName;
        faithtrack = new FaithTrack(initialFaithPoints);
        depots = new Depots();
        try {
            depots.addResourcesFromMarket(initialResources);
        }catch(UnableToFillException ignored){}
        try {
            personalBoard = new PersonalBoard( this.getPersonalBoard().getLeaderCards());
        } catch (NoMoreLeaderCardAliveException ignored) {}
        converter = new Converter(this);
        victoryPoints=0;
        discounted = new NumberOfResources();
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
    public void ActivateLeaderCard(int index) throws IllegalArgumentException, NoMoreLeaderCardAliveException {
        LeaderCard toActivate= getPersonalBoard().getLeaderCards()[index];
        toActivate.setPlayed(this);
    }


    public void addDiscount(ResourceType type, int discount){
        discounted = discounted.add(type, discount);
    }

    public NumberOfResources getDiscount(){
        return discounted;
    }
}