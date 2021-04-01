package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColor;

import java.util.ArrayList;

/*Last Edit: Fabio*/
public class Market {

    private MarbleColor[][] tray;
    private MarbleColor externalMarble;
    private static final int N_COL=4;
    private static final int N_ROW=3;

    //default constructor
    public Market() {
        tray = new MarbleColor[N_ROW][N_COL];
    }

    //getter
    public MarbleColor[] getRow(int index) {
        MarbleColor[] trayRow = new MarbleColor[3];
        for(int i= 0; i<N_COL; i++ ){
            trayRow[i] = tray[index][i];
        }
        return trayRow;
    }

    public MarbleColor[] getColumn(int index) {
        MarbleColor[] trayColumn = new MarbleColor[4];
        for(int i=0; i<N_ROW; i++){
            trayColumn[i] = tray[i][index];
        }
        return trayColumn;
    }

    /*Additional methods*/
    /**This allow to buy a row from the market's tray**/
    public ArrayList<MarbleColor> buyRow(int index) {                //TODO INSERIRE ECCEZIONI (?)
        ArrayList<MarbleColor> buyedRow = new ArrayList<>(4);

        for (int i=0; i<N_COL; i++){
            buyedRow.add(tray[index][i]);
        }
        tray[index][3]=externalMarble;
        for (int i=N_COL-2; i>=0; i--){
            tray[index][i]=buyedRow.get(i+1);
        }
        externalMarble=buyedRow.get(0);
        return buyedRow;
    }

    /*public ArrayList<MarbleColor> buyRow(int index) {
        ArrayList<MarbleColor> buyedRow = new ArrayList<>(4);

        for (MarbleColor x: tray[index]){
            buyedRow.add(x);
        }
        tray[index][3]=externalMarble;
        for (int i=tray[index].length-1; i<0;i--){
            tray[index][i]=buyedRow.get(i+1);
        }
        externalMarble=buyedRow.get(0);
        return buyedRow;
    }*/


    /**This allow to buy a column from the market's tray**/
    public ArrayList<MarbleColor> buyColumn(int index) {
        ArrayList<MarbleColor> buyedColumn = new ArrayList<>(3);

        for(int i=0; i<N_ROW; i++){
            buyedColumn.add(tray[i][index]);
        }
        tray[2][index]=externalMarble;
        for(int i=N_ROW-2; i>=0; i--){
            tray[i][index]=buyedColumn.get(i+1);
        }
        externalMarble=buyedColumn.get(0);
        return buyedColumn;
    }

}