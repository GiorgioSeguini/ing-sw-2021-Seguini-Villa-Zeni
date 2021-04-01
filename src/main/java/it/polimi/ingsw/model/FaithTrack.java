package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PopesFavorStates;
import it.polimi.ingsw.model.exception.FinalTurnException;
import it.polimi.ingsw.model.exception.PopesInspectionException;

import java.lang.reflect.Array;
import java.util.*;
/*Last Edit: Fabio*/  //ho soltanto tolto il metodo che aggiungeva due punti fede
/**
 * 
 */
public class FaithTrack {
    private static final int NUM_OF_POP = 3;
    private static final int MAX_POINTS = 20;

    private static final int[] victoryPoints = {0,0,0};   //TODO
    private static final int[] popesFavorPoints = {2,3,4};
    private static final int[] popesFavorPosition = {8, 12, 16};
    private static final int[] popesFavorInitialPosition = {3, 5, 7};
    private int faithPoints;
    private final PopesFavorStates[] popesFavor;

    /**
     * Default constructor
     */
    public FaithTrack(int initialFaithPoints) {
        popesFavor = new PopesFavorStates[3];
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
     *
     */
    public void addPoint() throws PopesInspectionException {
        if(faithPoints<MAX_POINTS) faithPoints++;
        for(int i=0; i<NUM_OF_POP; i++){
            if(faithPoints==popesFavorPosition[i] && popesFavor[i] == PopesFavorStates.FaceDown)
                throw new PopesInspectionException(i);
        }
    }

    public void popeInspection(int index) throws FinalTurnException {
        if(faithPoints>=popesFavorInitialPosition[index])
            popesFavor[index]=PopesFavorStates.FaceUp;
        else{
            popesFavor[index]=PopesFavorStates.FaceDown;
        }
        if(faithPoints==MAX_POINTS)
            throw new FinalTurnException();
    }

}