package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.MarbleColor;

import java.util.ArrayList;

/*Last Edit: Fabio*/
public class Market {

    MarbleColor[][] tray;
    MarbleColor externalMarble;

    private static final int N_COL=4;
    private static final int N_ROW=3;

    //default constructor

    public Market(ArrayList<MarbleColor> startMarbles) {
        //startMarbles.size() is 13
        tray= new MarbleColor[N_ROW][N_COL];
        setMarbles(startMarbles);
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
    }

}