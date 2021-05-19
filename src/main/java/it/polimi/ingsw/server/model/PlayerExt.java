package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.PlayerMessage;
import it.polimi.ingsw.constant.model.*;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*Last Edit: Fabio*/
public class PlayerExt extends Player implements Observable<Message> {

    private static final AtomicInteger nextID = new AtomicInteger();

    private transient final Converter converter;
    private transient NumberOfResources discounted;
    private transient ProductionPowerExt toActive;

    /*Default constructor*/
    public PlayerExt(String userName){
        super.setUserName(userName);
        super.setID(nextID.getAndIncrement());
        super.setPersonalBoard(new PersonalBoardExt(super.getID()));
        super.setFaithTrack(new FaithTrackExt(super.getID()));
        super.setDepots(new DepotsExt(super.getID()));
        super.setErrorMessage(ErrorMessage.NoError);
        super.setStatus(PlayerStatus.Waiting);

        this.converter = new Converter(this);
        this.discounted = new NumberOfResources();
    }



    /**
     * @return true if the player own enough resources to active the power production chosen
     */
    public boolean isActivable(){
        if(toActive==null)
            return false;

        NumberOfResources temp = super.getDepots().getResources();
        try{
            temp = temp.sub(toActive.getInputRes());
        }catch (OutOfResourcesException e){
            return false;
        }

        return temp.size()>=toActive.getOfYourChoiceInput();
    }

    public ProductionPowerExt getToActive() {
        return toActive;
    }

    public NumberOfResources getDiscounted() {
        return discounted;
    }

    @Override
    public FaithTrackExt getFaithTrack() {
        return (FaithTrackExt) super.getFaithTrack();
    }

    @Override
    public DepotsExt getDepots() {
        return (DepotsExt) super.getDepots();
    }

    @Override
    public PersonalBoardExt getPersonalBoard() {
        return (PersonalBoardExt) super.getPersonalBoard();
    }

    /*Modifier*/

    public void addDiscount(ResourceType type, int discount){
        discounted = discounted.add(type, discount);
    }

    @Override
    public void setStatus(PlayerStatus status) {
        super.setStatus(status);
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage()));
    }

    @Override
    public void setErrorMessage(ErrorMessage errorMessage) {
        super.setErrorMessage(errorMessage);
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage()));
    }

    public void setToActive(ProductionPowerExt toActive) {
        this.toActive = toActive;
    }

    public Converter getConverter() {
        return converter;
    }

    //Observable implementation
    private transient final List<Observer<Message>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<Message> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for(Observer<Message> observer : observers){
                observer.update(message);
            }
        }
    }
}