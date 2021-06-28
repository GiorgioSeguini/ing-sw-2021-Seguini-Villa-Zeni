package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.PlayerMessage;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Player ext class.
 * Extends Player and implements Observable interface.
 * Manage all the player's attribute.
 */
public class PlayerExt extends Player implements Observable<Message> {

    private static final AtomicInteger nextID = new AtomicInteger();

    /**
     * Instantiates a new Player ext.
     *
     * @param userName of type String: the player's user name.
     */
    /*Default constructor*/
    public PlayerExt(String userName){
        super.setUserName(userName);
        super.setID(nextID.getAndIncrement());
        super.setPersonalBoard(new PersonalBoardExt(super.getID()));
        super.setFaithTrack(new FaithTrackExt(super.getID()));
        super.setDepots(new DepotsExt(super.getID()));
        super.setErrorMessage(ErrorMessage.NoError);
        super.setStatus(PlayerStatus.Waiting);
        super.setConverter(new ConverterExt(this));
        super.setDiscounted(new NumberOfResources());
    }


    /**
     *
     * @return True if the player own enough resources to active the power production chosen.
     * False if the player hasn't any power production or hasn't enough resources.
     */
    public boolean isActivable(){
        if(getToActive()==null)
            return false;

        NumberOfResources temp = super.getDepots().getResources();
        try{
            temp = temp.sub(getToActive().getInputRes());
        }catch (OutOfResourcesException e){
            return false;
        }

        return temp.size()>= getToActive().getOfYourChoiceInput();
    }


    /**
     *
     * @return of type ProductionPowerExt: the production power the player own.
     */
    @Override
    public ProductionPowerExt getToActive() {
        return (ProductionPowerExt)super.getToActive();
    }

    /**
     *
     * @return of type FaithTrackExt: the player's faithtrack.
     */
    @Override
    public FaithTrackExt getFaithTrack() {
        return (FaithTrackExt) super.getFaithTrack();
    }

    /**
     *
     * @return of type DepotsExt: the player's depots.
     */
    @Override
    public DepotsExt getDepots() {
        return (DepotsExt) super.getDepots();
    }

    /**
     *
     * @return of type PersonalBoardExt: the player's personal board.
     */
    @Override
    public PersonalBoardExt getPersonalBoard() {
        return (PersonalBoardExt) super.getPersonalBoard();
    }

    /**
     *
     * @return of type ConverterExt: the player's converter.
     */
    @Override
    public ConverterExt getConverter() {
        return (ConverterExt) super.getConverter();
    }

    /*Modifier*/

    /**
     * Add discount.
     *
     * @param type of type ResourceType: the resource's type
     * @param discount of type int: the discount.
     */
    public void addDiscount(ResourceType type, int discount){
        super.setDiscounted(super.getDiscounted().add(type, discount));
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage(), this.getToActive(), this.getDiscounted()));
    }

    /**
     * Set the new player's status and notify it.
     *
     * @param status of type PlayerStatus: the player's status that has to be set.
     */
    @Override
    public void setStatus(PlayerStatus status) {
        super.setStatus(status);
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage(), this.getToActive(), this.getDiscounted()));
    }

    /**
     * Set the new error message and notify it.
     *
     * @param errorMessage of type ErrorMessage: the error message that has to be set.
     */
    @Override
    public void setErrorMessage(ErrorMessage errorMessage) {
        super.setErrorMessage(errorMessage);
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage(), this.getToActive(), this.getDiscounted()));
    }

    //Observable implementation
    private transient final List<Observer<Message>> observers = new ArrayList<>();

    /**
     *
     * @param observer of type Observer<Message>: the observer to add
     */
    @Override
    public void addObserver(Observer<Message> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     *
     * @param message of type Message: the notifying message
     */
    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for(Observer<Message> observer : observers){
                observer.update(message);
            }
        }
    }
}