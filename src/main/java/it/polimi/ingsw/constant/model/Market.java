package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.MarbleColor;

import java.util.ArrayList;

/**
 * Market class.
 * Superclass of MarketExt.
 */
public class Market {

    final MarbleColor[][] tray;
    MarbleColor externalMarble;
    public static final int N_COL=4;
    public static final int N_ROW=3;

    //default constructor
    /**
     * Instantiates a new Market.
     *
     * @param startMarbles of type ArrayList<MarbleColor>: the starting marbles.
     */
    public Market(ArrayList<MarbleColor> startMarbles) {
        //startMarbles.size() is 13
        tray= new MarbleColor[N_ROW][N_COL];
        setMarbles(startMarbles);
    }

    /**
     * Get the row selected from the market tray.
     *
     * @param index of type int: the row's
     * @return of type MarbleColor[]: the marble's array.
     */
//getter
    public MarbleColor[] getRow(int index) {
        MarbleColor[] trayRow = new MarbleColor[4];
        System.arraycopy(tray[index], 0, trayRow, 0, N_COL);
        return trayRow;
    }

    /**
     * Get the column selected from the market tray.
     *
     * @param index of type int: the column's
     * @return of type MarbleColor[]: the marble's array.
     */
    public MarbleColor[] getColumn(int index) {
        MarbleColor[] trayColumn = new MarbleColor[3];
        for(int i=0; i<N_ROW; i++){
            trayColumn[i] = tray[i][index];
        }
        return trayColumn;
    }

    /**
     * Gets the external marble.
     *
     * @return of type MarbleColor: the external marble.
     */
    public MarbleColor getExternalMarble() {
        return externalMarble;
    }

    /**
     * Get the marble in the selected row and column.
     *
     * @param row of type int: the row.
     * @param col of type int: the col.
     * @return of type MarbleColor: the marble.
     */
    public MarbleColor getMarble(int row, int col){
        return tray[row][col];
    }

    /**
     * Set a marble in a specific position of the market's tray.
     *
     * @param row of type int: the row.
     * @param col of type int: the col.
     * @param color of type MarbleColor: the color.
     */
    public void setMarble(int row, int col, MarbleColor color){
        tray[row][col]=color;
    }

    /**
     * Sets external marble.
     *
     * @param externalMarble of type MarbleColor: the external marble.
     */
    public void setExternalMarble(MarbleColor externalMarble) {
        this.externalMarble = externalMarble;
    }

    /**
     * Set marbles.
     *
     * @param marbles of type ArrayList<MarbleColor>: the marbles.
     */
    public void setMarbles(ArrayList<MarbleColor> marbles){
        for(int i=0; i<N_ROW; i++){
            for(int j=0; j<N_COL; j++){
                tray[i][j] = marbles.get(i*N_COL+j);
            }
        }
        externalMarble = marbles.get(N_COL*N_ROW);
    }

    @Override
    public String toString(){
        String tray="";
        for(int i=0;i<N_ROW;i++){
            int count=0;
            MarbleColor[] marbleColors= getRow(i);
            for(MarbleColor x: marbleColors) {
                tray += "\t"+x.getChar();
                count++;
                if(count==4) tray += " <- "+(i+1)+"\n";
            }
        }
        tray += "\t^\t^\t^\t^\t "+externalMarble.getChar()+"\n";
        tray+="\t|\t|\t|\t|\n";
        tray+="\t1\t2\t3\t4\n";
        return tray;
    }

}