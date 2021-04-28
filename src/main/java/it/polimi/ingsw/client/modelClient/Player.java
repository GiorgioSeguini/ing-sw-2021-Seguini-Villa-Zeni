package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.client.modelClient.enumeration.ErrorMessage;
import it.polimi.ingsw.client.modelClient.enumeration.PlayerStatus;
import it.polimi.ingsw.client.modelClient.enumeration.ResourceType;

/*Last Edit: Fabio*/
public class Player {

    private final String userName;
    private final FaithTrack faithtrack;
    private final Depots depots;
    private final PersonalBoard personalBoard;

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


}