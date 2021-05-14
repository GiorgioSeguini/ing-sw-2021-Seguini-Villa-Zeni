package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.PlayerMessage;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.observer.Observable;

import java.util.concurrent.atomic.AtomicInteger;

/*Last Edit: Fabio*/
public class PlayerExt extends Player implements Observable<Message> {

    private static final AtomicInteger nextID = new AtomicInteger();

    private transient final Converter converter;
    private transient NumberOfResources discounted;
    private transient ProductionPower toActive;

    /*Default constructor*/
    public PlayerExt(String userName){
        super.setUserName(userName);
        super.setID(nextID.getAndIncrement());
        super.setPersonalBoard(new PersonalBoardExt(super.getID()));
        super.setFaithtrack(new FaithTrackExt(super.getID()));
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


    /*Modifier*/

    public void addDiscount(ResourceType type, int discount){
        discounted = discounted.add(type, discount);
    }

    @Override
    public void setStatus(PlayerStatus status) {
        super.setStatus(status);
        notify(new PlayerMessage(this.getStatus(), this.getID()));
    }

    public void setToActive(ProductionPower toActive) {
        this.toActive = toActive;
    }

    public Converter getConverter() {
        return converter;
    }
}