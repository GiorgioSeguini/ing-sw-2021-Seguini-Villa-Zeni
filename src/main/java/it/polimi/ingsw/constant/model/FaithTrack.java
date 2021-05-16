package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.PopesFavorStates;

/**
 * 
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

    public PopesFavorStates getPopesFavor(int index) {
        return popesFavor[index];
    }

    public void setPopesFavor(int index, PopesFavorStates state){
        popesFavor[index]=state;
    }

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