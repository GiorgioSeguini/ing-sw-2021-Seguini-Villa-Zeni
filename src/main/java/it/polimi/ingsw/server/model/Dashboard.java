package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.message.DashBoardMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.parse.Starter;

import java.util.*;

/*Last Edit: Gio*/

public class Dashboard extends Observable<Message> {

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

        change();
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
        change();
    }

    public DevelopmentCard findDevCard(int id){
        for (Level l: Level.values()){
            for( ColorDevCard c: ColorDevCard.values()){
                for (DevelopmentCard x: dashBoard[l.ordinal()][c.ordinal()]){
                    if(x.getId()==id){
                        return x;
                    }
                }
            }
        }
        return null;
    }


    private void change(){
        ArrayList<String> cards = new ArrayList<>();
        for(Level l : Level.values()) {
            for (ColorDevCard c : ColorDevCard.values()) {
                cards.add(Starter.toJson(getTopDevCard(c, l)));
            }
        }

        notify(new DashBoardMessage(cards));
    }
    //            green   blu     Yellow  Purple
    //Level 3:
    @Override
    public String toString(){
        String res="";
        res+="\t\t\tGreen \tBlu \tYellow \tPurple";
        res+="Level 3:\t"+getTopDevCard(ColorDevCard.GREEN,Level.THREE);

        return res;
    }

}