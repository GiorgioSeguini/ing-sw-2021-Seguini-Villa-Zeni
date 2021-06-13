package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.message.MarketMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.model.Market;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*Last Edit: Fabio*/
public class MarketExt extends Market implements Observable<Message> {

    //default constructor

    public MarketExt(ArrayList<MarbleColor> startMarbles) {
        //startMarbles.length is 13
        super(startMarbles);
        Collections.shuffle(startMarbles);
        super.setMarbles(startMarbles);
    }


    /*Additional methods*/

    /**
     * This allow to buy a row from the market's tray
     **/
    public ArrayList<MarbleColor> buyRow(int index) {
        ArrayList<MarbleColor> buyedRow = new ArrayList<>(4);

        for (int i = 0; i < N_COL; i++) {
            buyedRow.add(super.gettMarble(index, i));
        }
        super.setMarble(index, N_COL - 1, super.getExternalMarble());
        for (int i = N_COL - 2; i >= 0; i--) {
            setMarble(index, i, buyedRow.get(i + 1));
        }
        super.setExternalMarble(buyedRow.get(0));
        change();
        return buyedRow;
    }

    /**
     * This allow to buy a column from the market's tray
     **/
    public ArrayList<MarbleColor> buyColumn(int index) {
        ArrayList<MarbleColor> buyedColumn = new ArrayList<>(3);

        for (int i = 0; i < N_ROW; i++) {
            buyedColumn.add(gettMarble(i, index));
        }
        super.setMarble(N_ROW - 1, index, super.getExternalMarble());
        for (int i = N_ROW - 2; i >= 0; i--) {
            super.setMarble(i, index, buyedColumn.get(i + 1));
        }
        setExternalMarble(buyedColumn.get(0));
        change();
        return buyedColumn;
    }

    /**
     * this methods should be call each time a changement in status happened, in order to correctly notify all the observer
     */
    private void change() {
        ArrayList<MarbleColor> marbles = new ArrayList<MarbleColor>();
        for(int i=0; i<N_ROW; i++){
            marbles.addAll(Arrays.asList(getRow(i)));
        }
        marbles.add(getExternalMarble());
        notify(new MarketMessage(marbles));
    }

    //Observable implementation
    private transient final List<Observer<Message>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<Message> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void notify(Message message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }
    }

}