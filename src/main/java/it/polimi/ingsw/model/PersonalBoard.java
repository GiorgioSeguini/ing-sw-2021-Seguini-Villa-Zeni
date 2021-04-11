package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.LeaderStatus;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.model.exception.NoSpaceException;

import java.util.*;

/*Last Edit: Fabio*/
public class PersonalBoard {

    private ArrayList<DevelopmentCard>[] OwnedDevCards;

    private LeaderCard[] OwnedLeaderCard;

    private ArrayList<ProductionPower> extraProduction;

    /*Default Constructor*/
    public PersonalBoard(LeaderCard[] startLeaderCards) {
        OwnedDevCards = new ArrayList[3]; //array di arraylist
        OwnedLeaderCard = new LeaderCard[2];
        for(int i=0; i<OwnedLeaderCard.length; i++){
            OwnedLeaderCard[i] = startLeaderCards[i];
        }
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

    public ArrayList<DevelopmentCard>[] getOwnedDevCards() {
        return OwnedDevCards.clone();
    }

    /**Those methods allow to get top DevCard of the stacks on the player's personal board**/
    public DevelopmentCard getTopDevCard(int index) {
        DevelopmentCard topdevcard = null;
        if(goodindex(index)){
            topdevcard = OwnedDevCards[index].get(0);
        }
        return topdevcard;
    }

    /**
    public DevelopmentCard getMidDevCard(int index) {
        DevelopmentCard midevcard = null;
        if(goodindex(index)){
            midevcard = OwnedDevCards[index].get(1);
        }
        return midevcard;
    }

    public DevelopmentCard getLastDevCard(int index) {
        DevelopmentCard lastdevcard = null;
        if(goodindex(index)){
        lastdevcard = OwnedDevCards[index].get(2);
        }
        return lastdevcard;
    }**/

    /**This allow to get all DevCard on the player's personal board**/
    public ArrayList<DevelopmentCard> getAllDevCard() {
        ArrayList<DevelopmentCard> AllDevCard = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for( int j=0; j<2; j++)
                AllDevCard.add(getOwnedDevCards()[i].get(j));
            //AllDevCard.addAll((Collection<? extends DevelopmentCard>) OwnedDevCards[i].clone());    //DA RIVEDERE
        }
        return AllDevCard;
    }

    /**This return the sum of card's victory points**/
    public int getVictoryPoints() {
        int victorypoints = 0;
        for (DevelopmentCard developmentCard : getAllDevCard()) {
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

    public ArrayList<ProductionPower> getProduction(){
        ArrayList<ProductionPower> res = (ArrayList<ProductionPower>) extraProduction.clone();

        for(int i=0; i<3; i++){
            res.add(getTopDevCard(i).getProductionPower());
        }

        return res;
    }
}
