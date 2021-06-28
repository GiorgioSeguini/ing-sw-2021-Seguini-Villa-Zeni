package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.message.DashBoardMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Dashboard;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Dashboard class Ext: extends Dashboard and implements Observable interface.
 * Manage all the Development card that a player can buy during the game.
 */

public class DashboardExt extends Dashboard implements Observable<Message> {

    private final Stack<DevelopmentCardExt>[][] dashBoard;

    /**
     * Default constructor, ensure correct classification of cards and randomness
     * @param developmentCards arraylist containing all the development card for this dashboard
     */
    public DashboardExt(ArrayList<DevelopmentCardExt> developmentCards) {
        //initialization
        dashBoard = new Stack[Level.size()][ColorDevCard.size()]; //number of level * number of colors
        for(Level l : Level.values()){
            for(ColorDevCard c: ColorDevCard.values()){
                dashBoard[l.ordinal()][c.ordinal()] = new Stack<>();
            }
        }

        //load cards
        for(DevelopmentCardExt card: developmentCards){
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
     *
     * @param color of type ColorDevCard: color of the card wanted.
     * @param level of type Level: level of che card wanted
     * @return of type DevelopmentCardExt: the card at the top of the stack.
     */
    @Override
    public DevelopmentCardExt getTopDevCard(ColorDevCard color, Level level) {
        int size = dashBoard[level.ordinal()][color.ordinal()].size();
        if(size ==0) return null;
        return dashBoard[level.ordinal()][color.ordinal()].get(size -1);
    }

    /**
     *
     * @return True if at least one dashboard's slot is empty. Otherwise False
     */
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
    public DevelopmentCardExt buyDevCard(ColorDevCard color, Level level) throws IllegalArgumentException{
        /*DevelopmentCard result = getTopDevCard(color, level);
        dashBoard[level.ordinal()][color.ordinal()].remove(result);
        return result;*

         */
        if(dashBoard[level.ordinal()][color.ordinal()].empty())
            throw new IllegalArgumentException();
        DevelopmentCardExt cardRemoved = dashBoard[level.ordinal()][color.ordinal()].pop();
        change();
        return cardRemoved;
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

    /**
     *
     * @param id of type int: card's id
     * @return the DevelopmentCardExt corresponding to that id.
     */
    public DevelopmentCardExt findDevCard(int id){
        for (Level l: Level.values()){
            for( ColorDevCard c: ColorDevCard.values()){
                for (DevelopmentCardExt x: dashBoard[l.ordinal()][c.ordinal()]){
                    if(x.getId()==id){
                        return x;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Notify any change in dashboard.
     */
    private void change(){
        notify(new DashBoardMessage(this));
    }

    //Observable implementation
    private transient final List<it.polimi.ingsw.server.observer.Observer<Message>> observers = new ArrayList<>();

    /**
     *
     * @param observer of type Observer<Message>: the observer to add
     */
    @Override
    public void addObserver(it.polimi.ingsw.server.observer.Observer<Message> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     *
     * @param message of type Message: the notifying message
     */
    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for(Observer<Message> observer : observers){
                observer.update(message);
            }
        }
    }
}