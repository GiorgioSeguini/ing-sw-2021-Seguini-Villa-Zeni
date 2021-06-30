package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
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

    @Test
    void SetPlayedTest() {
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

        ArrayList<LeaderCard> ownedLeaderCard = personalBoard.getLeaderCards();

        for (LeaderCard leaderCard : ownedLeaderCard) {
            LeaderCardExt leaderCardExt = (LeaderCardExt) leaderCard;
            if (leaderCardExt.setPlayed(player)) {
                assertEquals(LeaderStatus.Played, leaderCardExt.getStatus());
            } else {
                assertFalse(LeaderStatus.onHand == leaderCardExt.getStatus() & leaderCardExt.getRequirements().match(player));
            }
        }
        for (LeaderCard y: ownedLeaderCard) {
            LeaderCardExt x = (LeaderCardExt) y;
            if (x.getRequirements().match(player)) {
                assertFalse(x.setPlayed(player));
                assertNotEquals(LeaderStatus.onHand, x.getStatus());
            }
        }


    }


    @Test
    void SetDiscardTest() {
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
        ArrayList<LeaderCard> ownedLeaderCard = personalBoard.getLeaderCards();


        for(LeaderCard y: ownedLeaderCard){
            LeaderCardExt x =(LeaderCardExt) y;
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

        PlayerExt player = new PlayerExt("Pippo");

        player.getPersonalBoard().addLeaderCard(new LeaderCardExt[]{card1, card3});
        card1.setDiscard(player);

        assertEquals(1, player.getPersonalBoard().getLeaderCards().size());

        assertFalse(card1.setDiscard(player));


    }
}


