package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {
    @Test
    void Constructor(){
        DepotsAbility depotsAbility = new DepotsAbility(ResourceType.Stones);
        LeaderCardExt leaderCard = new LeaderCardExt(new RequirementsExt(new NumberOfResources(0,0,5,0)),depotsAbility,3);
        assertNotNull(leaderCard.getStatus());
        assertTrue(leaderCard.getStatus()==LeaderStatus.Dead||leaderCard.getStatus()==LeaderStatus.onHand||leaderCard.getStatus()==LeaderStatus.Played);
        assertNotNull(leaderCard.getRequirements());
        assertNotNull(leaderCard.getAbility());
        assertNotNull(leaderCard.getVictoryPoints());
        assertTrue(leaderCard.getVictoryPoints()>0);
    }

    //TODO da rivedere
    @Test
    void SetPlayedTest() throws IOException{
        ArrayList<LeaderCardExt> leaderCards;
        leaderCards = Starter.LeaderCardsParser();
        LeaderCardExt leaderCard1;
        LeaderCardExt leaderCard2;
        int n = (int) (Math.random() * 15.1);
        leaderCard1 = leaderCards.get(n);
        System.out.println(leaderCard1);
        leaderCards.remove(n);
        n=(int) (Math.random() * 14.1);
        leaderCard2 = leaderCards.get(n);
        leaderCards.remove(n);
        LeaderCardExt[] leaderCardsOw = new LeaderCardExt[] {leaderCard1,leaderCard2};

        PersonalBoardExt personalBoard = new PersonalBoardExt(0);
        personalBoard.addLeaderCard(leaderCardsOw);
        PlayerExt player = new PlayerExt("Fabio");
        player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));

        LeaderCardExt[] ownedLeaderCard = new LeaderCardExt[2];

        //try {
            ownedLeaderCard = personalBoard.getLeaderCards();
        /*} catch (NoMoreLeaderCardAliveException e) {
            e.printStackTrace();
        }*/


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
        for (LeaderCardExt x: ownedLeaderCard) {
            if (x.getRequirements().match(player)) {
                assertFalse(x.setPlayed(player));
                assertNotEquals(LeaderStatus.onHand, x.getStatus());
            }
        }


    }


    @Test
    void SetDiscardTest() throws IOException{
        ArrayList<LeaderCardExt> leaderCards;
        leaderCards = Starter.LeaderCardsParser();
        LeaderCardExt leaderCard1;
        LeaderCardExt leaderCard2;
        int n = (int) (Math.random() * 15.1);
        leaderCard1 = leaderCards.get(n);
        leaderCards.remove(n);
        n=(int) (Math.random() * 14.1);
        leaderCard2 = leaderCards.get(n);
        leaderCards.remove(n);
        LeaderCardExt[] leaderCardsOw = new LeaderCardExt[] {leaderCard1,leaderCard2};

        PersonalBoardExt personalBoard = new PersonalBoardExt(0);
        personalBoard.addLeaderCard(leaderCardsOw);
        PlayerExt player = new PlayerExt("Fabio");
        player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        int faithpoints=player.getFaithTrack().getFaithPoints();
        LeaderCardExt[] ownedLeaderCard = new LeaderCardExt[2];

        //try {
            ownedLeaderCard = personalBoard.getLeaderCards();
        /*} catch (NoMoreLeaderCardAliveException e) {
            e.printStackTrace();
        }*/

        for(LeaderCardExt x: ownedLeaderCard){
            if (x.setDiscard(player)){
                assertEquals(player.getFaithTrack().getFaithPoints(), ++faithpoints);
                assertFalse(player.getFaithTrack().getFaithPoints()<0 && player.getFaithTrack().getFaithPoints()>=25);
                assertEquals(LeaderStatus.Dead,x.getStatus());
                assertFalse(x.getStatus()==LeaderStatus.onHand || x.getStatus()==LeaderStatus.Played);
            }else
                assertNotSame(LeaderStatus.onHand, x.getStatus());
        }

        for (LeaderCardExt x: ownedLeaderCard){
            assertEquals(LeaderStatus.Dead,x.getStatus());
        }

        /*try {
            ownedLeaderCard = personalBoard.getLeaderCards();
            fail();
        } catch (NoMoreLeaderCardAliveException ignored) {}*/


    }

    @Test
    void equalsTest(){
        //test equality


        LeaderCardExt card1 = new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0);
        LeaderCardExt card2 = new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0);
        LeaderCardExt card3 = new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 1);
        LeaderCardExt card4 = new LeaderCardExt(new RequirementsExt(), new DepotsAbility(ResourceType.Coins), 0);
        LeaderCardExt card5 = new LeaderCardExt(new RequirementsExt(new NumberOfResources(3,3,3,3)), new WhiteAbility(ResourceType.Coins), 0);

        assertEquals(card1, card1);
        assertEquals(card2, card1);
        assertNotEquals(card3, new Object());
        assertNotEquals(card3, card1);
        assertNotEquals(card4, card1);
        assertNotEquals(card5, card1);

        PlayerExt player = new PlayerExt("Pippo");

        player.getPersonalBoard().addLeaderCard(new LeaderCardExt[]{card1, card3});
        card1.setDiscard(player);

        //try {
            assertEquals(1, player.getPersonalBoard().getLeaderCards().length);
       /* } catch (NoMoreLeaderCardAliveException e) {
            fail();
        }*/

        assertFalse(card1.setDiscard(player));


    }
}


