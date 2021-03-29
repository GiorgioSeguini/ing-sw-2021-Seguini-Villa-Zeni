package it.polimi.ingsw.model;

import java.util.*;
/*Last Edit: Fabio*/  //ho soltanto tolto il metodo che aggiungeva due punti fede
/**
 * 
 */
public class FaithTrack {
    private static final int NUM_OF_POP = 3;
    private static final int MAX_POINTS = 20;

    private static int[] victoryPoints = {0,0,0};   //TODO
    private static int[] popesFavorPoints = {2,3,4};
    private int faithPoints;
    private ArrayList<PopesFavorStates> popesFavor;

    /**
     * Default constructor
     */
    public FaithTrack(int initialFaithPoints) {
        faithPoints=initialFaithPoints;
        for(int i=0; i<NUM_OF_POP; i++) {
            try {
                popesFavor.add(PopesFavorStates.FaceDown);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
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
        for(PopesFavorStates p : popesFavor){
            if(p == PopesFavorStates.FaceUp){
                result += popesFavorPoints[popesFavor.indexOf(p)];
            }
        }
        return result;
    }

    /**
     * return true if you need to activate Pope's Inspection
     * @return
     */
    public boolean addPoint() throws IllegalArgumentException{
        if(faithPoints >= MAX_POINTS)
            throw new IllegalArgumentException();
        faithPoints++;
        if(faithPoints==0){
            //TODO
            return true;
        }
        return false;
    }

}