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
    public DevelopmentCard buyDevCard(ColorDevCard color, Level level) throws IllegalArgumentException{
        /*DevelopmentCard result = getTopDevCard(color, level);
        dashBoard[level.ordinal()][color.ordinal()].remove(result);
        return result;*

         */
        if(dashBoard[level.ordinal()][color.ordinal()].empty())
            throw new IllegalArgumentException();
        return dashBoard[level.ordinal()][color.ordinal()].pop();
    }

    //remove two devcard when discard2 token is activeted
    public void removeCard(ColorDevCard color, int n) throws IllegalArgumentException{      //TODO valutere se cambiare nome
        Level l = Level.One;
        while(n>0){
            try{
                buyDevCard(color, l);
                n--;
            }catch(IllegalArgumentException e){
                l = l.getNext();
            }
        }

    }

}