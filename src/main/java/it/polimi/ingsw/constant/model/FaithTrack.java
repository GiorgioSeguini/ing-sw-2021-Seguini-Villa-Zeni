package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.PopesFavorStates;

/**
 * FaithTrack class.
 * Superclass of FaithTrackExt (server).
 */
public class FaithTrack {
    public static final int NUM_OF_POP = 3;
    public static final int MAX_POINTS = 24;

    public static final int[] victoryPoints = {0,0,0, 1, 1,1,2,2,2,4,4,4,6,6,6,9,9,9,12,12,12,16,16,16,20};
    public static final int[] popesFavorPoints = {2,3,4};
    public static final int[] popesFavorPosition = {8, 16, 24};
    public static final int[] popesFavorInitialPosition = {5, 12, 19};
    private int faithPoints;
    private final PopesFavorStates[] popesFavor;

    public int[] getPopesFavorPosition() {
         return popesFavorPosition;
    }

    public int[] getPopesFavorInitialPositionPosition() {
        return popesFavorInitialPosition;
    }
    /**
     * Default constructor
     */
    public FaithTrack() {
        popesFavor = new PopesFavorStates[NUM_OF_POP];
        faithPoints = 0;
        for(int i=0; i<NUM_OF_POP; i++) {
                popesFavor[i]= PopesFavorStates.FaceDown;
        }
    }


    /**
     * Gets the faith points.
     * @return of type int: the faith points.
     */
    public int getFaithPoints() {
        return faithPoints;
    }

    /**
     * Gets the number of victory points from the faith track.
     * @return of type int: victory points.
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
     * Gets the popes favor state.
     * @param index of type int: the index.
     * @return of type PopesFavorStates: the popes favor state
     */
    public PopesFavorStates getPopesFavor(int index) {
        return popesFavor[index];
    }

    /**
     * Set the popes favor.
     * @param index of type int: the index.
     * @param state of type PopesFavorStates: the popes favor state to set.
     */
    public void setPopesFavor(int index, PopesFavorStates state){
        popesFavor[index]=state;
    }

    /**
     * Set the faith points.
     * @param faithPoints of type int: the faith points.
     */
    public void setFaithPoints(int faithPoints) {
        this.faithPoints = faithPoints;
    }

    @Override
    public String toString(){
        String FT = "";
        FT += "Faithpoints: "+getFaithPoints()+"\n";
        FT += "FaithTrack's victory points: "+getVictoryPoints()+"\n";
        return  FT;
    }
}