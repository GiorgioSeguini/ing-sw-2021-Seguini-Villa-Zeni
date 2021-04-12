package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;

import java.util.*;

/*Last Edit: Gio*/

public class Dashboard {

    private Stack<DevelopmentCard>[][] dashBoard;

    /**
     * Default constructor
     */
    public Dashboard(ArrayList<DevelopmentCard> developmentCards) {
        dashBoard = new Stack[Level.size()][ColorDevCard.size()]; //number of level * number of colors
        for(Level l : Level.values()){
            for(ColorDevCard c: ColorDevCard.values()){
                dashBoard[l.ordinal()][c.ordinal()] = new Stack<>();
            }
        }
        for(DevelopmentCard card: developmentCards){
            dashBoard[card.getLevel().ordinal()][card.getColor().ordinal()].add(card);
        }
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
        Level l = Level.ONE;
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