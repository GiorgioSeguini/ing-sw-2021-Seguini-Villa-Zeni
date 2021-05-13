package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.PlayerMessage;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.observer.Observable;

import java.util.concurrent.atomic.AtomicInteger;

/*Last Edit: Fabio*/
public class Player extends Observable<Message> {

    private static final AtomicInteger nextID = new AtomicInteger();

    private final String userName;
    private final FaithTrack faithtrack;
    private final Depots depots;
    private final PersonalBoard personalBoard;
    private transient final Converter converter;
    private final int ID;
    private NumberOfResources discounted;
    private ProductionPower toActive;

    private PlayerStatus status;
    private ErrorMessage errorMessage;

    /*Default constructor*/
    public Player(String userName){
        this.userName=userName;
        this.ID = nextID.getAndIncrement();
        this.personalBoard = new PersonalBoard(this.ID);
        this.faithtrack = new FaithTrack(this.ID);
        this.depots = new Depots(this.ID);
        this.converter = new Converter(this);
        this.discounted = new NumberOfResources();
        this.status = PlayerStatus.Waiting;
        this.errorMessage = ErrorMessage.NoError;
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
        return personalBoard.getVictoryPoints() + faithtrack.getVictoryPoints() + depots.getVictoryPoints();
    }

    public NumberOfResources getDiscount(){
        return discounted;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public ProductionPower getToActive() {
        return toActive;
    }

    public ErrorMessage getErrorMessage(){
        return errorMessage;
    }

    public int getID(){
        return ID;
    }

    /**
     * @return true if the player own enough resources to active the power production chosen
     */
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


    /*Modifier*/

    public void addDiscount(ResourceType type, int discount){
        discounted = discounted.add(type, discount);
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
        notify(new PlayerMessage(this.status, this.ID));
    }

    public void setToActive(ProductionPower toActive) {
        this.toActive = toActive;
    }

    public void setErrorMessage(ErrorMessage error){
        this.errorMessage=error;
    }

    /*@Override
    public String toString(){
        String PL = "";
        PL += "Username: "+getUserName()+"\n";
        PL += "\tID: "+getID()+"\n";
        PL += "Status: "+getStatus()+"\n";
        PL += "Error message: "+getErrorMessage()+"\n";
        PL += "Victory points: "+getVictoryPoints()+"\n";
        PL += "PersonalBoard:\n";
        PL += "\t"+getPersonalBoard()+"\n";
        PL += "FaithTrack:\n";
        PL += "\t"+getFaithTrack()+"\n";
        //PL += "Depots:\n";
        PL += ""+getDepots()+"\n";
        return PL;
    }*/
}