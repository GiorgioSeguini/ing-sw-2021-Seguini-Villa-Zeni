package it.polimi.ingsw.model;

import java.util.*;
/*Last Edit: Fabio*/

public class Market {

    private MarbleColor[][] tray;
    private MarbleColor externalMarble;

    //default constructor
    public Market() {
        tray = new MarbleColor[3][4];
    }

    //getter
    public MarbleColor[] getRow(int index) throws IllegalArgumentException{
        MarbleColor[] trayRow = new MarbleColor[3];
        for(int i= 0; i<4; i++ ){
            trayRow[i] = tray[index][i];
        }
        return trayRow;
    }

    public MarbleColor[] getColumn(int index) throws IllegalArgumentException{
        MarbleColor[] trayColumn = new MarbleColor[4];
        for(int i=0; i<3; i++){
            trayColumn[i] = tray[i][index];
        }
        return trayColumn;
    }

    /**
     * @return
     */
    public MarbleColor[] buyRow(int index) throws IllegalArgumentException {                //TODO INSERIRE ECCEZIONI
        MarbleColor[] buyedRow = new MarbleColor[4];
        for(int i=0; i<4; i++){
            buyedRow[i] = tray[index][i];               //COPIO LA RIGA DA COMPRARE IN BUYEDROW
        }
        MarbleColor oldExternalMarble = externalMarble; //MI SALVO LA VECCHIA EXTERNALMARBLE E AGGIORNO EXTERNALMARBLE CON QUELLA NUOVA
        externalMarble = tray[index][0];
        for(int i=0; i<3; i++){
            tray[index][i] = buyedRow[i+1];             //SPOSTO LA RIGA COMPRATA A SINISTRA DI UNA BIGLIA E INSERISCO LA VECCHIA EXTERNALMARBLE
        }
        tray[index][3] = oldExternalMarble;
        return buyedRow;
    }

    /**
     * @return
     */
    public MarbleColor[] buyColumn(int index) throws IllegalArgumentException {
        MarbleColor[] buyedColumn = new MarbleColor[3];
        for(int i=0; i<3; i++){
            buyedColumn[i] = tray[i][index];               //COPIO LA RIGA DA COMPRARE IN BUYEDROW
        }
        MarbleColor oldExternalMarble = externalMarble; //MI SALVO LA VECCHIA EXTERNALMARBLE E AGGIORNO  EXTERNALMARBLE CON QUELLA NUOVA
        externalMarble = tray[0][index];
        for(int i=0; i<2; i++){
            tray[i][index] = buyedColumn[i+1];             //SPOSTO LA RIGA COMPRATA A SINISTRA DI UNA BIGLIA E INSERISCO LA VECCHIA EXTERNALMARBLE
        }
        tray[2][index] = oldExternalMarble;
        return buyedColumn;
    }

}