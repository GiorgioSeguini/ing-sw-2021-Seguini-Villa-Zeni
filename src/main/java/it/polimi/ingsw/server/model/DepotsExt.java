package it.polimi.ingsw.server.model;


import it.polimi.ingsw.constant.message.DepotsMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Depots;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Shelf;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Depots class Ext.
 * Extends Depots and implements Observable interface.
 * Manage all the resource that a player store during the game.
 */
public class DepotsExt extends Depots implements Observable<Message> {
    private transient final int ownerID;

    /**
     * Default constructor
     */
    public DepotsExt(int ownerID) {
        this.ownerID=ownerID;
        super.setStrongBox(new StrongBoxExt());
        super.setWareHouseDepots(new WareHouseDepotsExt());

    }

    /**
     * @param input resources to add
     */
    public void addResourceFromProduction(NumberOfResources input) {
        ((StrongBoxExt)super.getStrongBox()).addResource(input);
        change();
    }

    /**
     *
     * @param input resources to add
     * @throws IllegalArgumentException if you can't add this resources
     */
    public void addResourcesFromMarket(NumberOfResources input) throws UnableToFillException {
        ((WareHouseDepotsExt) super.getWareHouseDepots()).addResource(input);
        change();
    }

    /**
     * @param required number of resources to subtract
     */
    public void subResource(NumberOfResources required) throws OutOfResourcesException {
        try {
            ((WareHouseDepotsExt) super.getWareHouseDepots()).subResource(required);
        }catch(OutOfResourcesException e){
            //remove as match resources possible from the warehouse depots
            NumberOfResources missing = required.safe_sub((super.getWareHouseDepots()).getResources());
            NumberOfResources subtracted = new NumberOfResources();
            try {
                subtracted = required.sub(missing);
            }catch(OutOfResourcesException ignored){}

            ((StrongBoxExt) super.getStrongBox()).subResource(missing);        //it can throws an exception but in this case I just rethrow it, I can't do anything differently

            //if no exception, I need to really update wareHouseDepots
            ((WareHouseDepotsExt) super.getWareHouseDepots()).subResource(subtracted);

        }
        change();
    }

    /**
     * @param required resources needed
     * @return true if the depots contains enough resources
     */
    public boolean match(NumberOfResources required) {
        NumberOfResources currentResources = getResources();
        try{
            currentResources.sub(required);
        }catch(OutOfResourcesException e){
            return false;
        }
        return true;
    }

    /**
     * @param s shelf to add
     */
    public void addExtraShelf(Shelf s){
        ((WareHouseDepotsExt) super.getWareHouseDepots()).addExtraShelf(s);
    }

    /**
     * This methods create an instance of FaithTrackMessage and notify observers
     */
    private void change(){
        notify(new DepotsMessage(this, this.ownerID));
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