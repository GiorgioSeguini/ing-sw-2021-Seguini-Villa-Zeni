package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;

import java.util.ArrayList;

/*Last Edit: Fabio*/
public class PersonalBoard {

    public static final int MAX_LEAD_CARD = 2;
    public static final int MAX_DEV_CARD = 7;

    protected final ArrayList<DevelopmentCard>[] OwnedDevCards;

    protected final ArrayList<LeaderCard> OwnedLeaderCard;

    private final ArrayList<ProductionPower> extraProduction;

    /*Default Constructor*/
    public PersonalBoard(){
        OwnedDevCards = new ArrayList[3]; //array di arraylist
        for( int i=0; i< 3; i++){
            OwnedDevCards[i] = new ArrayList<DevelopmentCard>();
        }

        OwnedLeaderCard = new ArrayList<>();
        extraProduction = new ArrayList<>();
    }


    /*Getter*/
    public ArrayList<LeaderCard> getLeaderCards(){
        return OwnedLeaderCard;
    }

    public ArrayList<DevelopmentCard> getOwnedDevCards() {
        ArrayList<DevelopmentCard> result = new ArrayList<DevelopmentCard>();
        for(int i=0; i< 3; i++)
            result.addAll(OwnedDevCards[i]);
        return result;
    }
    public DevelopmentCard getTopDevCard(int index){
        if(index<0 || index>3){
            return null;
        }
        return OwnedDevCards[index].get(OwnedDevCards[index].size() -1);
    }

    public ArrayList<DevelopmentCard> getActiveOwnedDevCards() {
        ArrayList<DevelopmentCard> result = new ArrayList<DevelopmentCard>();
        for(int i=0; i< OwnedDevCards.length; i++)
            if(OwnedDevCards[i].size()>0){
                result.add(OwnedDevCards[i].get(OwnedDevCards[i].size()-1));
            }
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

    public void addExtraProduction(ProductionPower productionPower){
        extraProduction.add(productionPower);
    }


    /**
     *
     * @return an ArrayList containing all the activable productionPower
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
     *
     * @return true if the leader card have already been chosen
     */
    public boolean isReady(){
        return OwnedLeaderCard.size()==MAX_LEAD_CARD;
    }

    public void setLeaderCards(ArrayList<LeaderCard> cards){
        OwnedLeaderCard.clear();
        OwnedLeaderCard.addAll(cards);
    }


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
