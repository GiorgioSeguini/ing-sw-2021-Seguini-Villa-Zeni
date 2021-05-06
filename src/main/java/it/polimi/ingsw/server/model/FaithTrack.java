package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.message.FaithTrackMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.parse.Starter;
/*Last Edit: Fabio*/
/**
 * 
 */
public class FaithTrack extends Observable<Message> {
    private static final int NUM_OF_POP = 3;
    private static final int MAX_POINTS = 24;

    private static final int[] victoryPoints = {0,0,0, 1, 1,1,2,2,2,4,4,4,6,6,6,9,9,9,12,12,12,16,16,16,20};
    private static final int[] popesFavorPoints = {2,3,4};
    private static final int[] popesFavorPosition = {8, 16, 24};
    private static final int[] popesFavorInitialPosition = {5, 12, 19};

    private final transient int ownerID;
    private int faithPoints;
    private final PopesFavorStates[] popesFavor;

    /**
     * Default constructor
     */
    public FaithTrack(int ownerID) {
        this.ownerID= ownerID;
        popesFavor = new PopesFavorStates[NUM_OF_POP];
        faithPoints = 0;
        for(int i=0; i<NUM_OF_POP; i++) {
                popesFavor[i]=PopesFavorStates.FaceDown;
        }
    }


    /**
     * @return number of faith points
     */
    public int getFaithPoints() {
        return faithPoints;
    }

    /**
     * @return number of victory points
     */
    public int getVictoryPoints() {
        int result = victoryPoints[faithPoints];
        for(int i=0; i<NUM_OF_POP; i++){
            if(popesFavor[i] == PopesFavorStates.FaceUp){
                result += popesFavorPoints[i];
            }
        }
        return result;
    }

    /**
     * after each call, caller is expected to call inspectionNeed() at a proper time
     */
    public void addPoint() {
        if(faithPoints<MAX_POINTS){
            faithPoints++;
            change();
        }
    }

    /**
     *
     * @return -1 if no inspection is needed, otherwise return 0, 1 or 2 depending on the number of the inspection needed
     */
    int inspectionNeed(){
        for(int i=0; i<NUM_OF_POP; i++) {
            if (faithPoints >= popesFavorPosition[i] && popesFavor[i] == PopesFavorStates.FaceDown)
                return i;
        }
        return -1;
    }

    void popeInspection(int index){
        if(faithPoints>=popesFavorInitialPosition[index])
            popesFavor[index]=PopesFavorStates.FaceUp;
        else{
            popesFavor[index]=PopesFavorStates.Discarded;
        }
        change();
    }

    boolean isEnd(){
        return faithPoints==MAX_POINTS;
    }

    /**
     * This methods create an instance of FaithTrackMessage and notify observers
     */
    private void change(){
        String faithTrack = Starter.toJson(this, FaithTrack.class);
        notify(new FaithTrackMessage(faithTrack, this.ownerID));
    }
}