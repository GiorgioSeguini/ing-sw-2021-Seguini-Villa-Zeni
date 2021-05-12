package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.util.ArrayList;

/*Last Edit: Fabio*/
public class PersonalBoard {

    public static final int MAX_LEAD_CARD = 2;
    private static final int MAX_DEV_CARD = 7;

    private ArrayList<DevelopmentCard>[] OwnedDevCards;

    private LeaderCard[] OwnedLeaderCard;

    private ArrayList<ProductionPower> extraProduction;

    /*Default Constructor*/
    public PersonalBoard(){
        OwnedDevCards = new ArrayList[3]; //array di arraylist
        for( int i=0; i< 3; i++){
            OwnedDevCards[i] = new ArrayList<DevelopmentCard>();
        }

        OwnedLeaderCard = new LeaderCard[2];
        extraProduction = new ArrayList<>();
        extraProduction.add(new ProductionPower(0, new NumberOfResources(), new NumberOfResources(), 2, 1));
    }


    /*Getter*/
    public LeaderCard[] getLeaderCards(){
        int deadLeaderCards = 0;
        for(int i=0; i< OwnedLeaderCard.length; i++){
            if(OwnedLeaderCard[i].getStatus() == LeaderStatus.Dead) deadLeaderCards++;
        }
        if (deadLeaderCards == 1) {
            LeaderCard[] returnedLeaderCard = new LeaderCard[1];
            if (OwnedLeaderCard[0].getStatus() == LeaderStatus.Dead) {
                returnedLeaderCard[0] = OwnedLeaderCard[1];
                return returnedLeaderCard;
            }
            returnedLeaderCard[0] = OwnedLeaderCard[0];
            return returnedLeaderCard;
        }
        return OwnedLeaderCard;
    }

    public ArrayList<DevelopmentCard> getOwnedDevCards() {
        ArrayList<DevelopmentCard> result = new ArrayList<DevelopmentCard>();
        for(int i=0; i< 3; i++)
            result.addAll(OwnedDevCards[i]);
        return result;
    }


    /**This return the sum of card's victory points**/
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


    public boolean isFull(){
        return getOwnedDevCards().size()==MAX_DEV_CARD;
    }

    /**This for check the index**/
    private boolean goodindex(int index) throws IllegalArgumentException{
        if(index>2 || index<0) {
            throw new IllegalArgumentException(); //TODO
        }
        return true;
    }

    public void addExtraProduction(ProductionPower productionPower){
        extraProduction.add(productionPower);
    }

    /**
     *
     * @return an ArrayList containing all the activable productionPower
     */
    public ArrayList<ProductionPower> getProduction(){

        ArrayList<ProductionPower> res= new ArrayList<>();
        for (ProductionPower x: this.extraProduction){
            res.add(x);
        }

        for(int i=0; i<3; i++){
            if(!OwnedDevCards[i].isEmpty()){
                res.add(OwnedDevCards[i].get(0).getProductionPower());
            }
        }

        return res;
    }

    /**
     *
     * @return true if the leader card have already been chosen
     */
    public boolean isReady(){
        boolean res = true;
        for(LeaderCard card :OwnedLeaderCard){
            if(card==null)
                res=false;
        }

        return res;
    }

    public void setDevCards(ArrayList<DevelopmentCard> cards[]){
        for(int i=0; i<3; i++){
            this.OwnedDevCards[i].clear();
            this.OwnedDevCards[i].addAll(cards[i]);
        }
    }

    public void setLeaderCards(ArrayList<LeaderCard> cards){
        for(int i=0; i<MAX_LEAD_CARD; i++){
            if(cards.size()>i){
                OwnedLeaderCard[i] = cards.get(i);
            }
        }
    }

    /*@Override
    public String toString(){
        String PB = "";
        PB += "Card's victory points: "+getVictoryPoints()+"\n";
        PB += "N° LeaderCard on hand or played: "+getLeaderCards().length+"\n";

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
    }*/
}
