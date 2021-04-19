package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.LeaderStatus;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.model.exception.NoSpaceException;

import java.util.*;

/*Last Edit: Fabio*/
public class PersonalBoard {

    private static final int MAX_LEAD_CARD = 2;

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
    public LeaderCard[] getLeaderCards() throws NoMoreLeaderCardAliveException{
        int deadLeaderCards = 0;
        for(int i=0; i< OwnedLeaderCard.length; i++){
            if(OwnedLeaderCard[i].getStatus() == LeaderStatus.Dead) deadLeaderCards++;
        }
        switch (deadLeaderCards) {
            case 1:
                LeaderCard[] returnedLeaderCard = new LeaderCard[1];
                if(OwnedLeaderCard[0].getStatus() == LeaderStatus.Dead) {
                    returnedLeaderCard[0] = OwnedLeaderCard[1];
                    return returnedLeaderCard;
                }
                returnedLeaderCard[0] = OwnedLeaderCard[0];
                return returnedLeaderCard;
            case 2:
                throw new NoMoreLeaderCardAliveException();
        }
        return OwnedLeaderCard.clone();
    }

    public ArrayList<DevelopmentCard> getOwnedDevCards() {
        ArrayList<DevelopmentCard> result = new ArrayList<DevelopmentCard>();
        for(int i=0; i< 3; i++)
            result.addAll(OwnedDevCards[i]);
        return result;
    }

    /**Those methods allow to get top DevCard of the stacks on the player's personal board**/
    private DevelopmentCard getTopDevCard(int index) {
        DevelopmentCard topdevcard = null;
        if(goodindex(index)){
            topdevcard = OwnedDevCards[index].get(0);
        }
        return topdevcard;
    }

    /**This return the sum of card's victory points**/
    public int getVictoryPoints() {
        if(!isReady()) return 0;
        int victorypoints = 0;
        for (DevelopmentCard developmentCard : getOwnedDevCards()) {
            victorypoints += developmentCard.getVictoryPoints();
        }
        try {
            for (LeaderCard leaderCard : getLeaderCards()) {
                if(leaderCard.getStatus() == LeaderStatus.Played) victorypoints += leaderCard.getVictoryPoints();
            }
        } catch (NoMoreLeaderCardAliveException ignored) {}

        return victorypoints;
    }

    /**This for add a DevCard in a specific position**/
    public void addDevCard(DevelopmentCard card, int pos) throws NoSpaceException {
        if(goodindex(pos)){
            if (OwnedDevCards[pos].isEmpty() && card.getLevel() == Level.ONE) {
                OwnedDevCards[pos].add(card);
            } else
            if(OwnedDevCards[pos].get(0).getLevel().ordinal() == card.getLevel().ordinal()-1)
            {
                OwnedDevCards[pos].add(0, card);
            } else throw new NoSpaceException();
        }
    }

    /**
     *
     * @param leaderCard array containing the choosen leader card
     */
    public void addLeaderCard(LeaderCard[] leaderCard){
        if(isReady()){
            throw new IllegalArgumentException();
        }

        if(leaderCard.length!= MAX_LEAD_CARD){
            throw new IndexOutOfBoundsException();
        }

        for(int i=0; i<MAX_LEAD_CARD; i++) {
            leaderCard[i].setStatus(LeaderStatus.onHand);
            this.OwnedLeaderCard[i]= leaderCard[i];
        }
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
            try {
                res.add(getTopDevCard(i).getProductionPower());
            }catch(IndexOutOfBoundsException ignored){};
        }

        return res;
    }

}
