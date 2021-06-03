package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;

import java.util.ArrayList;

/*Last Edit: Fabio*/
public class PersonalBoard {

    public static final int MAX_LEAD_CARD = 2;
    public static final int MAX_DEV_CARD = 7;

    protected ArrayList<DevelopmentCard>[] OwnedDevCards;

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
        extraProduction.add(new ProductionPower(0, new NumberOfResources(), new NumberOfResources(), 2, 1));
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


    public ArrayList<ProductionPower> getExtraProduction() {
        return extraProduction;
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
        return OwnedLeaderCard.size()==MAX_LEAD_CARD;
    }

    public void setDevCards(ArrayList<DevelopmentCard> cards[]){
        for(int i=0; i<3; i++){
            this.OwnedDevCards[i].clear();
            this.OwnedDevCards[i].addAll(cards[i]);
        }
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
        PB += "N° LeaderCard on hand or played: "+getLeaderCards().size()+"\n";

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
