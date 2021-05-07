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

    //TODO ora come ora è tutta sbagliata e incompleta, devo cancellarla e rifarla tutta
    @Override
    public String toString(){
        String res="";
        res += "\t\t\tGreen \tBlu \tYellow \tPurple\n";
        res += "Level 3:\tF\tF\tF\tF\n";
        res += "Level 2:\tF\tF\tF\tF\n";
        res += "Level 1:\tF\tF\tF\tF\n";
        for(int i=0; i<Level.size(); i++) {
            for(int j=0; j<ColorDevCard.size(); j++) {
                if(dashBoard[i][j]==null){
                    if(i==0&&j==0){
                        res += "\t\t\tGreen \tBlu \tYellow \tPurple\n";
                        res += "Level 3:\tE\tF\tF\tF\n";
                        res += "Level 2:\tF\tF\tF\tF\n";
                        res += "Level 1:\tF\tF\tF\tF\n";
                    }
                    if(i==0&&j==1){
                        res += "\t\t\tGreen \tBlu \tYellow \tPurple\n";
                        res += "Level 3:\tF\tE\tF\tF\n";
                        res += "Level 2:\tF\tF\tF\tF\n";
                        res += "Level 1:\tF\tF\tF\tF\n";
                    }
                    if(i==0&&j==2){
                        res += "\t\t\tGreen \tBlu \tYellow \tPurple\n";
                        res += "Level 3:\tF\tF\tE\tF\n";
                        res += "Level 2:\tF\tF\tF\tF\n";
                        res += "Level 1:\tF\tF\tF\tF\n";
                    }
                    if(i==0&&j==3){
                        res += "\t\t\tGreen \tBlu \tYellow \tPurple\n";
                        res += "Level 3:\tF\tF\tF\tE\n";
                        res += "Level 2:\tF\tF\tF\tF\n";
                        res += "Level 1:\tF\tF\tF\tF\n";
                    }
                }
            }
        }
        return res;
    }


}