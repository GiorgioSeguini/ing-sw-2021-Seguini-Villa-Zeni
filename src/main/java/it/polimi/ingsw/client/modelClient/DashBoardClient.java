package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.Dashboard;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

import java.util.ArrayList;

/**
 * The type Dash board client.
 * Instead of storing all the Development card, store just the top one, the ones you can see in a normal game
 */
public class DashBoardClient extends Dashboard {

    private final DevelopmentCard[][] dashBoard;

    /**
     * Default constructor
     *
     * @param developmentCards arraylist containing all the development card for this dashboard
     */
    public DashBoardClient(ArrayList<DevelopmentCard> developmentCards) {
        //initialization
        dashBoard = new DevelopmentCard[Level.size()][ColorDevCard.size()];
        //load cards
        setCards(developmentCards);
    }


    /**
     * These methods is used from client side for checking if a player has enough resources to buy any of the available Development Card
     *
     * @param game the game of type GameClient
     * @return true if there is at least one card with a cost lower than player's resources, false otherwise
     */
    public boolean isSomethingBuyable(GameClient game){
        for(Level l : Level.values()){
            for(ColorDevCard c : ColorDevCard.values()){
                try {
                    NumberOfResources cost = dashBoard[l.ordinal()][c.ordinal()].getCost();
                    cost = cost.safe_sub(game.getMe().getDiscounted());
                    game.getMe().getDepots().getResources().sub(cost);
                    return true;
                } catch (OutOfResourcesException | NullPointerException ignored) {}
            }
        }
        return false;
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

    /**
     * @see Dashboard#getTopDevCard(ColorDevCard, Level)
     */
    @Override
    public DevelopmentCard getTopDevCard(ColorDevCard color, Level level) {
        return dashBoard[level.ordinal()][color.ordinal()];
    }

    @Override
    public String toString(){
        String res="----------------------------------\n";
        for(int i=0; i<Level.size(); i++){
            int t=1;
            for(int j=0; j<ColorDevCard.size(); j++){
                if(dashBoard[i][j]!=null) {
                    res += "" + t + ":\n";
                    res += dashBoard[i][j] + "\n";
                    res += "**********************\n";
                    t++;
                }
            }
        }
        return res;
    }
}
