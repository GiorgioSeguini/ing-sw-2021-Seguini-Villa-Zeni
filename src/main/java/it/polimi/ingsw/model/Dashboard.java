package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Gio*/

public class Dashboard {

    private ArrayList<DevelopmentCard> [][] dashBoard= new ArrayList[3][4]; //number of level * number of colors

    /**
     * Default constructor
     */
    public Dashboard() {
        //TODO
    }


    /**
     * @param color 
     * @param level 
     * @return
     */
    public DevelopmentCard getTopDevCard(ColorDevCard color, Level level) {
        return dashBoard[level.getIndex()][color.getIndex()].get(dashBoard[level.getIndex()][color.getIndex()].size() -1);
    }

    /**
     * @param color 
     * @param level 
     * @return
     */
    public DevelopmentCard buyDevCard(ColorDevCard color, Level level) {
        DevelopmentCard result = getTopDevCard(color, level);
        dashBoard[level.getIndex()][color.getIndex()].remove(result);
        return result;
    }

}