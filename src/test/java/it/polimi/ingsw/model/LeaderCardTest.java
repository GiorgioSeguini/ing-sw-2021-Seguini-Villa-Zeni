package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Starter;
import it.polimi.ingsw.model.enumeration.LeaderStatus;
import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {
    @Test
    void Constructor(){
        DepotsAbility depotsAbility = new DepotsAbility(ResourceType.Stones);
        LeaderCard leaderCard = new LeaderCard(new Requirements(new NumberOfResources(0,0,5,0)),depotsAbility,3);
        assertNotNull(leaderCard.getStatus());
        assertTrue(leaderCard.getStatus()==LeaderStatus.Dead||leaderCard.getStatus()==LeaderStatus.onHand||leaderCard.getStatus()==LeaderStatus.Played);
        assertNotNull(leaderCard.getRequirements());
        assertNotNull(leaderCard.getAbility());
        assertNotNull(leaderCard.getVictoryPoints());
        assertTrue(leaderCard.getVictoryPoints()>0);
    }

    //TODO da rivedere
    @Test
    void SetPlayedTest() throws IOException, ParseException {
        ArrayList<LeaderCard> leaderCards;
        leaderCards = Starter.LeaderCardsParser();
        LeaderCard leaderCard1;
        LeaderCard leaderCard2;
        int n = (int) (Math.random() * 15.1);
        leaderCard1 = leaderCards.get(n);
        leaderCards.remove(n);
        n=(int) (Math.random() * 14.1);
        leaderCard2 = leaderCards.get(n);
        leaderCards.remove(n);
        LeaderCard[] leaderCardsOw = new LeaderCard[] {leaderCard1,leaderCard2};

        PersonalBoard personalBoard = new PersonalBoard();
        personalBoard.addLeaderCard(leaderCardsOw);
        Player player = new Player("Fabio");
        player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));

        LeaderCard[] ownedLeaderCard = new LeaderCard[2];

        try {
            ownedLeaderCard = personalBoard.getLeaderCards();
        } catch (NoMoreLeaderCardAliveException e) {
            e.printStackTrace();
        }


        for(int i=0; i<ownedLeaderCard.length; i++) {
            if (ownedLeaderCard[i].setPlayed(player)) {
                assertEquals(LeaderStatus.Played, ownedLeaderCard[i].getStatus());
            }else{
                assertFalse(LeaderStatus.onHand == ownedLeaderCard[i].getStatus() & ownedLeaderCard[i].getRequirements().match(player));
                if (ownedLeaderCard[i].getStatus()!=LeaderStatus.onHand){
                    System.out.println("The card isn't onHand,\n The card is the one in position \n"+i);
                }else{
                    System.out.println("you don't have the right requirements,\n The card is the one in position \n"+i);
                }
            }
        }
        for (LeaderCard x: ownedLeaderCard) {
            if (x.getRequirements().match(player)) {
                assertFalse(x.setPlayed(player));
                assertNotEquals(LeaderStatus.onHand, x.getStatus());
            }
        }


    }


    @Test
    void SetDiscardTest() throws IOException, ParseException {
        ArrayList<LeaderCard> leaderCards;
        leaderCards = Starter.LeaderCardsParser();
        LeaderCard leaderCard1;
        LeaderCard leaderCard2;
        int n = (int) (Math.random() * 15.1);
        leaderCard1 = leaderCards.get(n);
        leaderCards.remove(n);
        n=(int) (Math.random() * 14.1);
        leaderCard2 = leaderCards.get(n);
        leaderCards.remove(n);
        LeaderCard[] leaderCardsOw = new LeaderCard[] {leaderCard1,leaderCard2};

        PersonalBoard personalBoard = new PersonalBoard();
        personalBoard.addLeaderCard(leaderCardsOw);
        Player player = new Player("Fabio");
        player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        int faithpoints=player.getFaithTrack().getFaithPoints();
        LeaderCard[] ownedLeaderCard = new LeaderCard[2];

        try {
            ownedLeaderCard = personalBoard.getLeaderCards();
        } catch (NoMoreLeaderCardAliveException e) {
            e.printStackTrace();
        }

        for(LeaderCard x: ownedLeaderCard){
            if (x.setDiscard(player)){
                assertEquals(player.getFaithTrack().getFaithPoints(), ++faithpoints);
                assertFalse(player.getFaithTrack().getFaithPoints()<0 && player.getFaithTrack().getFaithPoints()>=25);
                assertEquals(LeaderStatus.Dead,x.getStatus());
                assertFalse(x.getStatus()==LeaderStatus.onHand || x.getStatus()==LeaderStatus.Played);
            }else
                assertNotSame(LeaderStatus.onHand, x.getStatus());
        }

        for (LeaderCard x: ownedLeaderCard){
            assertEquals(LeaderStatus.Dead,x.getStatus());
        }

        try {
            ownedLeaderCard = personalBoard.getLeaderCards();
            fail();
        } catch (NoMoreLeaderCardAliveException ignored) {}


    }

    @Test
    void equalsTest(){
        //test equality


        LeaderCard card1 = new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0);
        LeaderCard card2 = new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0);
        LeaderCard card3 = new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 1);
        LeaderCard card4 = new LeaderCard(new Requirements(), new DepotsAbility(ResourceType.Coins), 0);
        LeaderCard card5 = new LeaderCard(new Requirements(new NumberOfResources(3,3,3,3)), new WhiteAbility(ResourceType.Coins), 0);

        assertEquals(card1, card1);
        assertEquals(card2, card1);
        assertNotEquals(card3, new Object());
        assertNotEquals(card3, card1);
        assertNotEquals(card4, card1);
        assertNotEquals(card5, card1);

        Player player = new Player("Pippo");

        player.getPersonalBoard().addLeaderCard(new LeaderCard[]{card1, card3});
        card1.setDiscard(player);

        try {
            assertEquals(1, player.getPersonalBoard().getLeaderCards().length);
        } catch (NoMoreLeaderCardAliveException e) {
            fail();
        }

        assertFalse(card1.setDiscard(player));


    }
}


