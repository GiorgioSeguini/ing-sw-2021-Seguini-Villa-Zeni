package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.client.modelClient.enumeration.MarbleColor;

import java.util.ArrayList;

/*Last Edit: Fabio*/
public class Market {

    MarbleColor[][] tray;
    MarbleColor externalMarble;

    private static final int N_COL=4;
    private static final int N_ROW=3;

    //default constructor

    public Market(ArrayList<MarbleColor> startMarbles) {
        //startMarbles.length is 13

        tray= new MarbleColor[N_ROW][N_COL];
        for(int i=0; i<N_ROW; i++){
            for(int j=0; j<N_COL; j++){
                tray[i][j] = startMarbles.get(i+j);
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

}