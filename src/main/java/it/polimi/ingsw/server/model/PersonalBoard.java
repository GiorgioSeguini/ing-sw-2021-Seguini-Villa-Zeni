package it.polimi.ingsw.server.model;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.message.PersonalBoardMessage;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.observer.Observable;
import it.polimi.ingsw.server.parse.Starter;

import java.lang.reflect.Type;
import java.util.*;

/*Last Edit: Fabio*/
public class PersonalBoard extends Observable<Message> {

    private static final int MAX_LEAD_CARD = 2;
    private static final int MAX_DEV_CARD = 7;

    private final int ownerID;
    private ArrayList<DevelopmentCard>[] OwnedDevCards;

    private LeaderCard[] OwnedLeaderCard;

    private ArrayList<ProductionPower> extraProduction;

    /*Default Constructor*/
    public PersonalBoard(int ownerID){
        this.ownerID=ownerID;
        OwnedDevCards = new ArrayList[3]; //array di arraylist
        for( int i=0; i< 3; i++){
            OwnedDevCards[i] = new ArrayList<DevelopmentCard>();
        }

        OwnedLeaderCard = new LeaderCard[2];
        extraProduction = new ArrayList<>();
        extraProduction.add(new ProductionPower(0, new NumberOfResources(), new NumberOfResources(), 2, 1));
    }


    /*Getter*/
    public LeaderCard[] getLeaderCards() throws NoMoreLeaderCardAliveException {
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
        change();
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
            throw new IllegalArgumentException();
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
     * This methods create an instance of PersonalBoardMessage and notify observer
     * @see LeaderCard only class that call this methods
     */
    protected void change(){
        Type type = new TypeToken<ArrayList<DevelopmentCard>[]>(){}.getType();
        String devCards = Starter.toJson(this.OwnedDevCards, type);

        Type type1 = new TypeToken<LeaderCard[]>(){}.getType();
        String leaderCards = Starter.toJson(this.OwnedLeaderCard, type1);
        notify(new PersonalBoardMessage(devCards, leaderCards, this.ownerID));
    }

}
