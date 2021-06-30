package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.PersonalBoardMessage;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.PersonalBoard;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PersonalBoardExt class.
 * Extends PersonalBoard and implements Observable interface.
 * Manage the player's personal board.
 */
public class PersonalBoardExt extends PersonalBoard implements Observable<Message> {

    private final transient int ownerID;


    /**
     * Instantiates a new Personal board ext.
     *
     * @param ownerID of type int: the owner id.
     */
    /*Default Constructor*/
    public PersonalBoardExt(int ownerID){
        super();
        this.ownerID=ownerID;
        super.addExtraProduction(new ProductionPowerExt(0, new NumberOfResources(), new NumberOfResources(), 2, 1));
    }


    /**
     * Add a DevCard in a specific position.
     *
     * @param card of type DevelopmentCard: the card that has to be added.
     * @param pos of type int: the position in the personal board where the card has to be placed
     * @throws NoSpaceException the no space exception, thrown when the personal board is full.
     * @throws IllegalArgumentException if is not valid.
     */
    public void addDevCard(DevelopmentCard card, int pos) throws NoSpaceException, IllegalArgumentException{
        if(goodindex(pos)){
            if (super.getPos(pos).isEmpty()){
                if(card.getLevel() == Level.ONE) {
                    super.getPos(pos).add(card);
                }else {
                    throw new NoSpaceException();
                }
            }else if(super.getPos(pos).get(0).getLevel().ordinal() == card.getLevel().ordinal()-1){
                super.getPos(pos).add(0, card);
            } else throw new NoSpaceException();
        }
        change();
    }

    /**
     * Add the two Leader Card chosen in the initial game.
     *
     * @param leaderCard of type LeaderCardExt[]: array containing the chosen leader card.
     */
    public void addLeaderCard(LeaderCardExt[] leaderCard){
        if(isReady()){
            throw new IllegalArgumentException();
        }

        if(leaderCard.length!= MAX_LEAD_CARD){
            throw new IllegalArgumentException();
        }

        for(int i=0; i<MAX_LEAD_CARD; i++) {
            leaderCard[i].setStatus(LeaderStatus.onHand);
        }
        super.setLeaderCards(new ArrayList<>(Arrays.asList(leaderCard)));
        change();
    }


    /**
     * Check the index is an int between 0 and 2.
     *
     * @param index of type int: the index that has to be checked.
     * @return True if the index is minor than 2 and major than 0. Including 0 and 2.
     * @throws IllegalArgumentException
     */
    private boolean goodindex(int index) throws IllegalArgumentException{
        if(index>2 || index<0) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    /**
     * Remove the leader card from OwnedLeaderCard.
     *
     * @param card of type LeaderCardExt: the card that has to be removed.
     */
    protected void setDiscard(LeaderCardExt card){
        OwnedLeaderCard.remove(card);
        change();
    }

    /**
     * This methods create an instance of PersonalBoardMessage and notify observer
     *
     */
    protected void change(){
        notify(new PersonalBoardMessage(this, this.ownerID));
    }

    //Observable implementation
    private transient final List<it.polimi.ingsw.server.observer.Observer<Message>> observers = new ArrayList<>();

    /**
     *
     * @param observer of type Observer<Message>: the observer to add
     */
    @Override
    public void addObserver(it.polimi.ingsw.server.observer.Observer<Message> observer){
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
