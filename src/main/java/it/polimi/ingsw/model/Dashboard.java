package it.polimi.ingsw.model;

import java.util.*;

/*Last Edit: Gio*/

public class Dashboard {

    private Stack<DevelopmentCard> [][] dashBoard= new Stack[ColorDevCard.size()][Level.size()]; //number of level * number of colors

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
        return dashBoard[level.ordinal()][color.ordinal()].get(dashBoard[level.ordinal()][color.ordinal()].size() -1);
    }

    /**
     * @param color 
     * @param level 
     * @return
     */
    public DevelopmentCard buyDevCard(ColorDevCard color, Level level) {
        /*DevelopmentCard result = getTopDevCard(color, level);
        dashBoard[level.ordinal()][color.ordinal()].remove(result);
        return result;*

         */
        return dashBoard[level.ordinal()][color.ordinal()].pop();
    }

}