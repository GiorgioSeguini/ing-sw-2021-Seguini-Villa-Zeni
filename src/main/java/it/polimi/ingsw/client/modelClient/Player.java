package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

/*Last Edit: Fabio*/
public class Player {

    private final String userName;
    private FaithTrack faithtrack;
    private Depots depots;
    private final PersonalBoard personalBoard;
    private final int ID;

    private PlayerStatus status;
    private ErrorMessage errorMessage;

    /*Default constructor*/
    public Player(String userName){
        this.userName=userName;
        this.personalBoard = new PersonalBoard();
        this.faithtrack = new FaithTrack();
        this.depots = new Depots();
        this.status = PlayerStatus.Waiting;
        this.errorMessage = ErrorMessage.NoError;
        this.ID  = 0;   //TODO
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
        return personalBoard.getVictoryPoints() + faithtrack.getVictoryPoints() + depots.getVictoryPoints();
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public ErrorMessage getErrorMessage(){
        return errorMessage;
    }

    public int getID() {
        return ID;
    }

    public void setFaithTrack(FaithTrack faithtrack){
        this.faithtrack= faithtrack;
    }

    public void setDepots(Depots depots){
        this.depots= depots;
    }
}