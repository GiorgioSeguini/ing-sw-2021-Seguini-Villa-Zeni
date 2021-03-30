package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColor;

import java.util.ArrayList;

/*Last Edit: Fabio*/
public class Market {

    private MarbleColor[][] tray;
    private MarbleColor externalMarble;
    private ArrayList<MarbleColor> buyedMarbleRow = new ArrayList<MarbleColor>(4);
    private ArrayList<MarbleColor> buyedMarbleColumn = new ArrayList<MarbleColor>(3);

    //default constructor
    public Market() {
        tray = new MarbleColor[3][4];
    }

    //getter
    public MarbleColor[] getRow(int index) {
        MarbleColor[] trayRow = new MarbleColor[3];
        for(int i= 0; i<4; i++ ){
            trayRow[i] = tray[index][i];
        }
        return trayRow;
    }

    public MarbleColor[] getColumn(int index) {
        MarbleColor[] trayColumn = new MarbleColor[4];
        for(int i=0; i<3; i++){
            trayColumn[i] = tray[i][index];
        }
        return trayColumn;
    }

    /*Additional methods*/
    /**This allow to buy a row from the market's tray**/
    public ArrayList<MarbleColor> buyRow(int index) {                //TODO INSERIRE ECCEZIONI (?)
        MarbleColor[] buyedRow = new MarbleColor[4];
        for(int i=0; i<4; i++){
            buyedRow[i] = tray[index][i];
        }
        MarbleColor oldExternalMarble = externalMarble;
        externalMarble = tray[index][0];
        for(int i=0; i<3; i++){
            tray[index][i] = buyedRow[i+1];
        }
        tray[index][3] = oldExternalMarble;
        for(int i=0; i<3; i++){
            buyedMarbleRow.add(i,buyedRow[i]);
        }
        return buyedMarbleRow;
    }

    /**This allow to buy a column from the market's tray**/
    public ArrayList<MarbleColor> buyColumn(int index) {
        MarbleColor[] buyedColumn = new MarbleColor[3];
        for(int i=0; i<3; i++){
            buyedColumn[i] = tray[i][index];
        }
        MarbleColor oldExternalMarble = externalMarble;
        for(int i=0; i<2; i++){
            tray[i][index] = buyedColumn[i+1];
        }
        tray[2][index] = oldExternalMarble;
        for(int i=0; i<2; i++){
            buyedMarbleRow.add(i,buyedColumn[i]);
        }
        return buyedMarbleColumn;
    }

}