package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.message.FaithTrackMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.FaithTrack;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.parse.Starter;

import java.util.ArrayList;
import java.util.List;
/*Last Edit: Fabio*/
/**
 * 
 */
public class FaithTrackExt extends FaithTrack implements Observable<Message> {

    private final transient int ownerID;


    /**
     * Default constructor
     */
    public FaithTrackExt(int ownerID) {
        super();
        this.ownerID= ownerID;
    }


    /**
     * after each call, caller is expected to call inspectionNeed() at a proper time
     */
    public void addPoint() {
        if(super.getFaithPoints()<MAX_POINTS){
            super.setFaithPoints(super.getFaithPoints() + 1);
            change();
        }
    }

    /**
     *
     * @return -1 if no inspection is needed, otherwise return 0, 1 or 2 depending on the number of the inspection needed
     */
    public int inspectionNeed(){
        for(int i=0; i<NUM_OF_POP; i++) {
            if (super.getFaithPoints() >= popesFavorPosition[i] && super.getPopesFavor(i) == PopesFavorStates.FaceDown)
                return i;
        }
        return -1;
    }

    public void popeInspection(int index){
        if(super.getFaithPoints()>=popesFavorInitialPosition[index])
            super.setPopesFavor(index , PopesFavorStates.FaceUp);
        else{
            super.setPopesFavor(index, PopesFavorStates.Discarded);
        }
        change();
    }

    public boolean isEnd(){
        return super.getFaithPoints()==MAX_POINTS;
    }

    /**
     * This methods create an instance of FaithTrackMessage and notify observers
     */
    private void change(){
        String faithTrack = Starter.toJson(this, FaithTrackExt.class);
        notify(new FaithTrackMessage(faithTrack, this.ownerID));
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