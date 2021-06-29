package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

/**
 * Player class.
 * Superclass of PlayerExt (server).
 */
public class Player {

    private String userName;
    private FaithTrack faithTrack;
    private Depots depots;
    private PersonalBoard personalBoard;
    private int ID;

    private PlayerStatus status;
    private ErrorMessage errorMessage;
    private Converter converter;
    private ProductionPower toActive;
    private NumberOfResources discounted;

    /**
     * Gets player's victory points.
     *
     * @return of type int: the victory point's amount.
     */
//smart getter
    public int getVictoryPoints(){
        return personalBoard.getVictoryPoints() + faithTrack.getVictoryPoints() + depots.getVictoryPoints();
    }

    /**
     * Gets player's user name.
     *
     * @return of type String: the user name.
     */
//getter
    public String getUserName() {
        return userName;
    }

    /**
     * Gets faith track.
     *
     * @return of type FaithTrack: the faith track.
     */
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    /**
     * Gets depots.
     *
     * @return of type Depots: the depot.
     */
    public Depots getDepots() {
        return depots;
    }

    /**
     * Gets personal board.
     *
     * @return of type PersonalBoard: the personal board.
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * Gets id.
     *
     * @return of type int: the player's id.
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets status.
     *
     * @return of type PlayerStatus: the player's status.
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * Gets error message.
     *
     * @return of type ErrorMessage: the error message.
     */
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    /**
     * Gets converter.
     *
     * @return of type Converter: the converter.
     */
    public Converter getConverter() {
        return converter;
    }

    /**
     * Gets to active.
     *
     * @return of type ProductionPower: the production power that is going to be activated.
     */
    public ProductionPower getToActive() {
        return toActive;
    }

    /**
     * Gets discounted.
     *
     * @return of type NumberOfResources: the discounted.
     */
    public NumberOfResources getDiscounted() {
        return discounted;
    }
    //SETTER

    /**
     * Sets user name.
     *
     * @param userName of type String: the user name.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets faith track.
     *
     * @param faithTrack of type FaithTrack: the faith track.
     */
    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    /**
     * Sets depots.
     *
     * @param depots of type Depots: the depots.
     */
    public void setDepots(Depots depots) {
        this.depots = depots;
    }

    /**
     * Sets personal board.
     *
     * @param personalBoard of type PersonalBoard: the personal board.
     */
    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    /**
     * Sets id.
     *
     * @param ID of type int: the player's id.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Sets status.
     *
     * @param status of type PlayerStatus: the status.
     */
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage of type ErrorMessage: the error message.
     */
    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Sets converter.
     *
     * @param converter of type Converter: the converter.
     */
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    /**
     * Sets to active.
     *
     * @param toActive of type ProductionPower: the production power.
     */
    public void setToActive(ProductionPower toActive) {
        this.toActive = toActive;
    }

    /**
     * Sets discounted.
     *
     * @param discounted of type NumberOfResources: the discounted.
     */
    public void setDiscounted(NumberOfResources discounted) {
        this.discounted = discounted;
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