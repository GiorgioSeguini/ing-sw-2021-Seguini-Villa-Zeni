package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
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
    private ProductionPower toActive;

    private PlayerStatus status;

    /*Default constructor*/
    public Player(String userName, PersonalBoard personalBoard, int initialFaithPoints, NumberOfResources initialResources) {
        this.userName=userName;
        this.personalBoard = personalBoard;
        faithtrack = new FaithTrack(initialFaithPoints);
        depots = new Depots();
        try {
            depots.addResourcesFromMarket(initialResources);
        }catch(UnableToFillException ignored){}
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

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public ProductionPower getToActive() {
        return toActive;
    }

    public void setToActive(ProductionPower toActive) {
        this.toActive = toActive;
    }

    public boolean isActivable(){
        if(toActive==null)
            return false;

        NumberOfResources temp = depots.getResources();
        try{
            temp = temp.sub(toActive.getInputRes());
        }catch (OutOfResourcesException e){
            return false;
        }

        return temp.size()>=toActive.getOfYourChoiceInput();
    }

    public boolean easyActive(){
        return toActive.getOfYourChoiceOutput()==0 && toActive.getOfYourChoiceInput()==0;
    }
}