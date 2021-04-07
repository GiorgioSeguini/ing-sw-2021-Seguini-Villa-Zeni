package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PopesFavorStates;
import it.polimi.ingsw.model.exception.FinalTurnException;


import java.lang.reflect.Array;
import java.util.*;
/*Last Edit: Fabio*/  //ho soltanto tolto il metodo che aggiungeva due punti fede
/**
 * 
 */
public class FaithTrack {
    private static final int NUM_OF_POP = 3;
    private static final int MAX_POINTS = 24;

    private static final int[] victoryPoints = {0,0,0, 1, 1,1,2,2,2,4,4,4,6,6,6,9,9,9,12,12,12,16,16,16,20};
    private static final int[] popesFavorPoints = {2,3,4};
    private static final int[] popesFavorPosition = {8, 16, 24};
    private static final int[] popesFavorInitialPosition = {5, 12, 19};
    private int faithPoints;
    private final PopesFavorStates[] popesFavor;

    /**
     * Default constructor
     */
    public FaithTrack(int initialFaithPoints) {
        if(initialFaithPoints<0)
            throw new IllegalArgumentException();
        popesFavor = new PopesFavorStates[NUM_OF_POP];
        faithPoints=initialFaithPoints;
        for(int i=0; i<NUM_OF_POP; i++) {
                popesFavor[i]=PopesFavorStates.FaceDown;
        }
    }


    /**
     * @return
     */
    public int getFaithPoints() {
        return faithPoints;
    }

    /**
     * @return
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
     *dopo ogni chiamata chiamate game.popesInspection!!!
     */
    public void addPoint() {
        if(faithPoints<MAX_POINTS) faithPoints++;
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

    void popeInspection(int index) throws FinalTurnException{
        if(faithPoints>=popesFavorInitialPosition[index])
            popesFavor[index]=PopesFavorStates.FaceUp;
        else{
            popesFavor[index]=PopesFavorStates.Discarded;
        }
        if(faithPoints==MAX_POINTS)
            throw new FinalTurnException();
    }

}