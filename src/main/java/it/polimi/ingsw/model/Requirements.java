package it.polimi.ingsw.model;

import java.util.*;

public class Requirements {

    private NumberOfResources numofres;
    private ArrayList<ColorDevCard> ColorCardReq;
    private ArrayList<Level> MinLevelReq;
    private ArrayList<Integer> MinNumCardReq;

    /*Default constructor*/
    Requirements() {
    }

    /*Getter*/
    public int getReq(ColorDevCard color) {
    }

    public int getReq(ColorDevCard color, Level level) {
        // TODO implement here
        return 0;
    }

    /*Additional Methods*/
    public boolean match(PersonalBoard board) {
        // TODO implement here
        return false;
    }

}