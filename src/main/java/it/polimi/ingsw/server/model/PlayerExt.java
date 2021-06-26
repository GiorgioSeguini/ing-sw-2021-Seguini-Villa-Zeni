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
     * @return true if the player own enough resources to active the power production chosen
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



    @Override
    public ProductionPowerExt getToActive() {
        return (ProductionPowerExt)super.getToActive();
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

    @Override
    public ConverterExt getConverter() {
        return (ConverterExt) super.getConverter();
    }

    /*Modifier*/

    public void addDiscount(ResourceType type, int discount){
        super.setDiscounted(super.getDiscounted().add(type, discount));
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage(), this.getToActive(), this.getDiscounted()));
    }

    @Override
    public void setStatus(PlayerStatus status) {
        super.setStatus(status);
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage(), this.getToActive(), this.getDiscounted()));
    }

    @Override
    public void setErrorMessage(ErrorMessage errorMessage) {
        super.setErrorMessage(errorMessage);
        notify(new PlayerMessage(this.getStatus(), this.getID(), this.getErrorMessage(), this.getToActive(), this.getDiscounted()));
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