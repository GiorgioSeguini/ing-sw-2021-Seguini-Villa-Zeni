package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.message.MarketMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.server.observer.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*Last Edit: Fabio*/
public class Market extends Observable<Message> {

    MarbleColor[][] tray;
    MarbleColor externalMarble;

    private static final int N_COL=4;
    private static final int N_ROW=3;

    //default constructor

    public Market(ArrayList<MarbleColor> startMarbles) {
        //startMarbles.length is 13
        Collections.shuffle(startMarbles);

        tray= new MarbleColor[N_ROW][N_COL];
        for(int i=0; i<N_ROW; i++){
            for(int j=0; j<N_COL; j++){
                tray[i][j] = startMarbles.get(i*N_COL+j);
            }
        }
        externalMarble = startMarbles.get(startMarbles.size()-1);
    }

    //getter
    public MarbleColor[] getRow(int index) {
        MarbleColor[] trayRow = new MarbleColor[4];
        for(int i= 0; i<N_COL; i++ ){
            trayRow[i] = tray[index][i];
        }
        return trayRow;
    }

    public MarbleColor[] getColumn(int index) {
        MarbleColor[] trayColumn = new MarbleColor[3];
        for(int i=0; i<N_ROW; i++){
            trayColumn[i] = tray[i][index];
        }
        return trayColumn;
    }

    /*Additional methods*/
    /**This allow to buy a row from the market's tray**/
    public ArrayList<MarbleColor> buyRow(int index) {
        ArrayList<MarbleColor> buyedRow = new ArrayList<>(4);

        for (int i=0; i<N_COL; i++){
            buyedRow.add(tray[index][i]);
        }
        tray[index][N_COL-1]=externalMarble;
        for (int i=N_COL-2; i>=0; i--){
            tray[index][i]=buyedRow.get(i+1);
        }
        externalMarble=buyedRow.get(0);
        change();
        return buyedRow;
    }

    /**This allow to buy a column from the market's tray**/
    public ArrayList<MarbleColor> buyColumn(int index) {
        ArrayList<MarbleColor> buyedColumn = new ArrayList<>(3);

        for(int i=0; i<N_ROW; i++){
            buyedColumn.add(tray[i][index]);
        }
        tray[N_ROW-1][index]=externalMarble;
        for(int i=N_ROW-2; i>=0; i--){
            tray[i][index]=buyedColumn.get(i+1);
        }
        externalMarble=buyedColumn.get(0);
        change();
        return buyedColumn;
    }

    /**
     * this methods should be call each time a changement in status happened, in order to correctly notify all the observer
     */
    private void change(){
        ArrayList<MarbleColor> marbles = new ArrayList<MarbleColor>();
        for(MarbleColor[] row : tray){
            marbles.addAll(Arrays.asList(row));
        }
        marbles.add(externalMarble);
        notify(new MarketMessage(marbles));
    }
    //TODO discutere lo switch case
    /*@Override
    public String toString(){
        String tray="";
        for(int i=0;i<N_ROW;i++){
            int count=0;
            MarbleColor[] marbleColors= getRow(i);
            for(MarbleColor x: marbleColors) {
                switch (x){
                    case RED:
                        tray+= "R\t";
                        break;
                    case BLUE:
                        tray+= "B\t";
                        break;
                    case GREY:
                        tray+= "G\t";
                        break;
                    case PURPLE:
                        tray+= "P\t";
                        break;
                    case WHITE:
                        tray+= "W\t";
                        break;
                    case YELLOW:
                        tray+= "Y\t";
                        break;
                }
                count++;
                if(count==4) tray += "<-\n";
            }
        }
        tray += "^\t^\t^\t^\t "+externalMarble+"\n";
        return tray;
    }*/
}