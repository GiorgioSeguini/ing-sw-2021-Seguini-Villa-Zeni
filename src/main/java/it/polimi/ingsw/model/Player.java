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
    private final PersonalBoard personalBoard;
    private final Converter converter;
    private NumberOfResources discounted;
    private ProductionPower toActive;

    private PlayerStatus status;

    /*Default constructor*/
    public Player(String userName){
        this.userName=userName;
        this.personalBoard = new PersonalBoard();
        this.faithtrack = new FaithTrack();
        this.depots = new Depots();
        this.converter = new Converter(this);
        this.discounted = new NumberOfResources();
        this.status = PlayerStatus.Waiting;
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
        int victoryPoints = personalBoard.getVictoryPoints() + faithtrack.getVictoryPoints() + depots.getVictoryPoints();
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