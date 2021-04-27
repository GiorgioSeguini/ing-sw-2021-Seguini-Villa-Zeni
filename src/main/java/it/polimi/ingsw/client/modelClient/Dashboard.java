package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.client.modelClient.enumeration.ColorDevCard;
import it.polimi.ingsw.client.modelClient.enumeration.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/*Last Edit: Gio*/

public class Dashboard {

    private final Stack<DevelopmentCard>[][] dashBoard;

    /**
     * Default constructor, ensure correct classification of cards and randomness
     * @param developmentCards arraylist containing all the development card for this dashboard
     */
    public Dashboard(ArrayList<DevelopmentCard> developmentCards) {
        //initialization
        dashBoard = new Stack[Level.size()][ColorDevCard.size()]; //number of level * number of colors
        for(Level l : Level.values()){
            for(ColorDevCard c: ColorDevCard.values()){
                dashBoard[l.ordinal()][c.ordinal()] = new Stack<>();
            }
        }

        //load cards
        for(DevelopmentCard card: developmentCards){
            dashBoard[card.getLevel().ordinal()][card.getColor().ordinal()].add(card);
        }

        //shuffle
        for(Level l : Level.values()){
            for(ColorDevCard c: ColorDevCard.values()){
                Collections.shuffle(dashBoard[l.ordinal()][c.ordinal()]);
            }
        }
    }


    /**
     * @param color of the card wanted
     * @param level of che card wanted
     * @return null if stack is empty, otherwise return the card in the top position, the one you can buy
     */
    public DevelopmentCard getTopDevCard(ColorDevCard color, Level level) {
        int size = dashBoard[level.ordinal()][color.ordinal()].size();
        if(size ==0) return null;
        return dashBoard[level.ordinal()][color.ordinal()].get(size -1);
    }

    public boolean isEmpty(){
        for(ColorDevCard color: ColorDevCard.values()){
            boolean empty = true;
            for(Level level : Level.values()){
                if(!dashBoard[level.ordinal()][color.ordinal()].isEmpty()){
                    empty=false;
                }
            }
            if(empty) return true;
        }
        return false;
    }

    /**
     * @param color of the card wanted
     * @param level of che card wanted
     * @return the card you wnat to buy, and remove it from the dashboard
     * @throws IllegalArgumentException when the card required is no more available
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

    /**
     * Methods used by the solo Action Token
     * @param color of the cards wanted to be removed
     * @param n number of the cards to remove
     * @throws IllegalArgumentException if there are not enough card of the required color
     */
    public void removeCard(ColorDevCard color, int n) throws IllegalArgumentException{
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