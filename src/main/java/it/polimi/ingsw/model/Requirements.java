package it.polimi.ingsw.model;

import java.util.*;

/**
 * 
 */
public class Requirements {

    /**
     * Default constructor
     */
    public Requirements() {
    }

    /**
     * 
     */
    private ArrayList<colors> resources;

    /**
     * 
     */
    private ArrayList<ColorDevCard> ColorCardReq;

    /**
     * 
     */
    private ArrayList<Level> MinLevelReq;

    /**
     * 
     */
    private ArrayList<int> MinNumCardReq;





    /**
     * @param color 
     * @return
     */
    public int getReq(ColorDevCard color) {
        // TODO implement here
        return 0;
    }

    /**
     * @param color 
     * @param level 
     * @return
     */
    public int getReq(ColorDevCard color, Level level) {
        // TODO implement here
        return 0;
    }

    /**
     * @param board 
     * @return
     */
    public boolean match(PersonalBoard board) {
        // TODO implement here
        return false;
    }

}