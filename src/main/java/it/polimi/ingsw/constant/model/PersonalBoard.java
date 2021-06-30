package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;

import java.util.ArrayList;

/**
 * PersonalBoard class.
 * Superclass of PersonalBoardExt.
 */
public class PersonalBoard {

    public static final int MAX_LEAD_CARD = 2;
    public static final int MAX_DEV_CARD = 7;
    protected final ArrayList<DevelopmentCard>[] OwnedDevCards;
    protected final ArrayList<LeaderCard> OwnedLeaderCard;
    private final ArrayList<ProductionPower> extraProduction;

    /**
     * Instantiates a new Personal board.
     */
    /*Default Constructor*/
    public PersonalBoard(){
        OwnedDevCards = new ArrayList[3]; //array di arraylist
        for( int i=0; i< 3; i++){
            OwnedDevCards[i] = new ArrayList<DevelopmentCard>();
        }

        OwnedLeaderCard = new ArrayList<>();
        extraProduction = new ArrayList<>();
    }


    /**
     * Get leader cards array list.
     *
     * @return of type ArrayList<LeaderCard>: the leader cards.
     */
    /*Getter*/
    public ArrayList<LeaderCard> getLeaderCards(){
        return OwnedLeaderCard;
    }

    /**
     * Gets owned development cards.
     *
     * @return of type ArrayList<DevelopmentCard>: the owned development cards.
     */
    public ArrayList<DevelopmentCard> getOwnedDevCards() {
        ArrayList<DevelopmentCard> result = new ArrayList<DevelopmentCard>();
        for(int i=0; i< 3; i++)
            result.addAll(OwnedDevCards[i]);
        return result;
    }

    /**
     * Get top development card.
     *
     * @param index of type int: the index.
     * @return of type DevelopmentCard: the development card.
     */
    public DevelopmentCard getTopDevCard(int index){
        if(index<0 || index>3){
            return null;
        }
        return OwnedDevCards[index].get(0);
    }

    /**
     * Gets active owned development cards.
     *
     * @return of type ArrayList<DevelopmentCard>: the active owned dev cards.
     */
    public ArrayList<DevelopmentCard> getActiveOwnedDevCards() {
        ArrayList<DevelopmentCard> result = new ArrayList<DevelopmentCard>();
        for(int i=0; i< OwnedDevCards.length; i++)
            if(OwnedDevCards[i].size()>0){
                result.add(OwnedDevCards[i].get(0));
            }
        return result;
    }


    /**
     * This return the sum of card's victory points.
     * @return of type int: the victory points.
     */
    public int getVictoryPoints() {
        int victorypoints = 0;
        for (DevelopmentCard developmentCard : getOwnedDevCards()) {
            victorypoints += developmentCard.getVictoryPoints();
        }

            for (LeaderCard leaderCard : getLeaderCards()) {
                if(leaderCard.getStatus() == LeaderStatus.Played) victorypoints += leaderCard.getVictoryPoints();
            }


        return victorypoints;
    }


    /**
     * Check if personal board is full.
     *
     * @return of type boolean: True if is full. Otherwise False.
     */
    public boolean isFull(){
        return getOwnedDevCards().size()==MAX_DEV_CARD;
    }

    /**
     * Add extra production.
     *
     * @param productionPower of type ProductionPower: the production power to add.
     */
    public void addExtraProduction(ProductionPower productionPower){
        extraProduction.add(productionPower);
    }


    /**
     * Get production array list.
     *
     * @return of type ArrayList<ProductionPower>: all the activable productionPower.
     */
    public ArrayList<ProductionPower> getProduction(){

        ArrayList<ProductionPower> res = new ArrayList<>(this.extraProduction);

        for(int i=0; i<3; i++){
            if(!OwnedDevCards[i].isEmpty()){
                res.add(OwnedDevCards[i].get(0).getProductionPower());
            }
        }

        return res;
    }

    /**
     * Check if the number of leader card at the beginning of the game is two.
     *
     * @return of type boolean: True if the leader card have already been chosen. Otherwise False.
     */
    public boolean isReady(){
        return OwnedLeaderCard.size()==MAX_LEAD_CARD;
    }

    /**
     * Set leader cards.
     *
     * @param cards of type ArrayList<LeaderCard>: the cards to set.
     */
    public void setLeaderCards(ArrayList<LeaderCard> cards){
        OwnedLeaderCard.clear();
        OwnedLeaderCard.addAll(cards);
    }


    /**
     * Gets development cards in a specific personal board's slot.
     *
     * @param pos of type int: the slot.
     * @return of type ArrayList<DevelopmentCard>: the developments cards in that slot.
     */
    public ArrayList<DevelopmentCard> getPos(int pos){
        return OwnedDevCards[pos];
    }

    @Override
    public String toString(){
        String PB = "";
        PB += "Card's victory points: "+getVictoryPoints()+"\n";
        PB += "N° LeaderCard played: "+getLeaderCards().size()+"\n";

        for(LeaderCard lc: getLeaderCards()) {
            PB += "\n" + lc + "\n";
        }
        PB += "************************************\n";
        PB += "N° DevelopmentCard on the top: "+getOwnedDevCards().size()+"\n";
        for (DevelopmentCard x: getOwnedDevCards()){
            PB += "\n"+x+"\n";
        }
        PB += "************************************\n";
        PB += "N° ProductionPower you can active: "+getProduction().size()+"\n";
        for (ProductionPower x: getProduction()){
            PB += "\n"+x+"\n";
        }
        PB += "************************************\n";
        return PB;
    }
}
