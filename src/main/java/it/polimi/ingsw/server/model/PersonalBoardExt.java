package it.polimi.ingsw.server.model;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.PersonalBoardMessage;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.PersonalBoard;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

import java.lang.reflect.Type;
import java.util.*;

/*Last Edit: Fabio*/
public class PersonalBoardExt extends PersonalBoard implements Observable<Message> {

    private final int ownerID;


    /*Default Constructor*/
    public PersonalBoardExt(int ownerID){
        super();
        this.ownerID=ownerID;
    }

    @Override
    public LeaderCardExt[] getLeaderCards() {
        return (LeaderCardExt[]) super.getLeaderCards();
    }


    /**This for add a DevCard in a specific position**/
    public void addDevCard(DevelopmentCard card, int pos) throws NoSpaceException {
        if(goodindex(pos)){
            if (super.getPos(pos).isEmpty() && card.getLevel() == Level.ONE) {
                super.getPos(pos).add(card);
            } else
            if(super.getPos(pos).get(0).getLevel().ordinal() == card.getLevel().ordinal()-1)
            {
                super.getPos(pos).add(0, card);
            } else throw new NoSpaceException();
        }
        change();
    }

    /**
     *
     * @param leaderCard array containing the choosen leader card
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
            super.setLeaderCard(leaderCard[i], i);
        }
        change();
    }



    /**This for check the index**/
    private boolean goodindex(int index) throws IllegalArgumentException{
        if(index>2 || index<0) {
            throw new IllegalArgumentException(); //TODO
        }
        return true;
    }


    /**
     * This methods create an instance of PersonalBoardMessage and notify observer
     * @see LeaderCard only class that call this methods
     */
    protected void change(){
        /*Type type = new TypeToken<ArrayList<DevelopmentCardExt>[]>(){}.getType();
        String devCards = Starter.toJson(super.getOwnedDevCards(), type);

        Type type1 = new TypeToken<LeaderCardExt[]>(){}.getType();
        String leaderCards = Starter.toJson(super.getLeaderCards(), type1);
        notify(new PersonalBoardMessage(devCards, leaderCards, this.ownerID));*/
    }

    //Observable implementation
    private transient final List<it.polimi.ingsw.server.observer.Observer<Message>> observers = new ArrayList<>();

    @Override
    public void addObserver(it.polimi.ingsw.server.observer.Observer<Message> observer){
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
