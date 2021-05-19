package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.ProductionPowerExt;

/*Last Edit: Fabio*/
public class Player {

    private String userName;
    private FaithTrack faithTrack;
    private Depots depots;
    private PersonalBoard personalBoard;
    private int ID;

    private PlayerStatus status;
    private ErrorMessage errorMessage;
    private Converter converter;
    private ProductionPowerExt toActive;

    //smart getter
    public int getVictoryPoints(){
        return personalBoard.getVictoryPoints() + faithTrack.getVictoryPoints() + depots.getVictoryPoints();
    }

    //getter
    public String getUserName() {
        return userName;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Depots getDepots() {
        return depots;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public int getID() {
        return ID;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public Converter getConverter() {
        return converter;
    }

    public ProductionPowerExt getToActive() {
        return toActive;
    }

    //SETTER

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public void setDepots(Depots depots) {
        this.depots = depots;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void setToActive(ProductionPowerExt toActive) {
        this.toActive = toActive;
    }

    @Override
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
    }
}