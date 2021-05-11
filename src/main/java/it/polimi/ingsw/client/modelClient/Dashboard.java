package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;

import java.util.ArrayList;

/*Last Edit: Gio*/

public class Dashboard {

    private final DevelopmentCard[][] dashBoard;
    public int[][] visualizedDasboard;

    /**
     * Default constructor, ensure correct classification of cards and randomness
     * @param developmentCards arraylist containing all the development card for this dashboard
     */
    public Dashboard(ArrayList<DevelopmentCard> developmentCards) {
        //initialization
        dashBoard = new DevelopmentCard[Level.size()][ColorDevCard.size()];
        visualizedDasboard = new int[Level.size()][ColorDevCard.size()];
        for(int i=0; i<Level.size(); i++){
            for(int j=0; j<ColorDevCard.size(); j++){
                visualizedDasboard[i][j] = 1;
            }
        }

        //load cards
        setCards(developmentCards);
    }


    /**
     * @param color of the card wanted
     * @param level of che card wanted
     * @return null if stack is empty, otherwise return the card in the top position, the one you can buy
     */
    public DevelopmentCard getTopDevCard(ColorDevCard color, Level level) {
        return dashBoard[level.ordinal()][color.ordinal()];
    }

    /**
     * This methods clear the dashboard and store the new cards, if a card is missing, its corresponding value will be set to null
     * @param developmentCards new cards to store
     */
    private void setCards( ArrayList<DevelopmentCard> developmentCards){
        //clear table
        for(Level l : Level.values()){
            for(ColorDevCard c : ColorDevCard.values()){
                dashBoard[l.ordinal()][c.ordinal()] = null;
            }
        }

        //load new cards
        for(DevelopmentCard card: developmentCards){
            dashBoard[card.getLevel().ordinal()][card.getColor().ordinal()] = card;
        }
    }

    /*@Override
    public String toString(){
        String res="----------------------------------\n";
        int k=3;
        for(int i=0; i<Level.size(); i++){
            int t=1;
            res+= "LEVEL "+k+": \n";
            res+= "\t1)GREEN, 2)BLU, 3)YELLOW, 4)PURPLE\n";
            res+= "----------------------------------\n";
            for(int j=0; j<ColorDevCard.size(); j++){
                res+=""+ t +":\n";
                res+= dashBoard[i][j]+"\n";
                res+="**********************\n";
                t++;
            }
            k--;
        }
        return res;
    }*/
}