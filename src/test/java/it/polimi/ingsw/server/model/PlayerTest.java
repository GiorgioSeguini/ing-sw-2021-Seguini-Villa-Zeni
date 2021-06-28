package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void Constructor() {
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

        //PersonalBoardExt personalBoard = new PersonalBoardExt(0);
        //personalBoard.addLeaderCard(leaderCardsOw);

        PlayerExt player1 = new PlayerExt("Fabio1");
        player1.getPersonalBoard().addLeaderCard(leaderCardsOw);
        System.out.println(player1);
        assertNotNull(player1.getUserName());
        assertNotNull(player1.getPersonalBoard());
        assertNotNull(player1.getDepots());
        assertNotNull(player1.getDepots().getResources());
        assertNotNull(player1.getFaithTrack());
        assertNotNull(player1.getConverter());
        assertNotNull(player1.getDiscounted());
        assertEquals(0,player1.getFaithTrack().getFaithPoints());
        assertTrue(player1.getVictoryPoints()>=0);
        assertEquals(0, player1.getDepots().getResources().size());
        assertEquals(PlayerStatus.Waiting, player1.getStatus());
        assertEquals(ErrorMessage.NoError, player1.getErrorMessage());
        assertNull(player1.getToActive());
        assertFalse(player1.isActivable());

    }

    @Test
    void GetterVictoryPoints() {
        PlayerExt player= new PlayerExt("Fabio");
        assertTrue(player.getVictoryPoints()==0);
        player.getDepots().addResourceFromProduction(new NumberOfResources(0,0,5,0));
        assertFalse(player.getVictoryPoints()==0);
        ArrayList<LeaderCardExt> leaderCards;
        leaderCards = Starter.LeaderCardsParser();
        LeaderCardExt leaderCard1;
        LeaderCardExt leaderCard2;
        leaderCard1 = leaderCards.get(0);
        leaderCard2 = leaderCards.get(1);
        LeaderCardExt[] leaderCardsOw = new LeaderCardExt[] {leaderCard1,leaderCard2};

        player.getPersonalBoard().addLeaderCard(leaderCardsOw);

        LeaderCardExt[] leaderCardsOw2 = new LeaderCardExt[2];
        int i=0;
        for (LeaderCard x: player.getPersonalBoard().getLeaderCards()) {
            assertNotNull(x);
            i++;
        }

        ((LeaderCardExt)player.getPersonalBoard().getLeaderCards().get(0)).setPlayed(player);
        assertTrue(player.getVictoryPoints()>0);
    }

    @Test
    void discountTest(){
        PlayerExt player = new PlayerExt("Pippo");

        assertEquals(new NumberOfResources(), player.getDiscounted());

        try{
            player.addDiscount(ResourceType.Coins, -1);
            fail();
        }catch(IllegalArgumentException ignored){}

        player.addDiscount(ResourceType.Coins, 1);

        assertEquals(new NumberOfResources(0, 0, 1, 0), player.getDiscounted());
    }

    @Test
    void productionTest(){
        PlayerExt player = new PlayerExt("Pippo");

        assertNull(player.getToActive());

        //test1
        ProductionPowerExt power = new ProductionPowerExt(0, new NumberOfResources(1, 2, 3, 4), new NumberOfResources(1, 0, 0, 0));
        player.setToActive(power);

        assertEquals(power, player.getToActive());
        assertFalse(player.isActivable());

        try {
            player.getDepots().addResourcesFromMarket(new NumberOfResources(1, 0, 0, 0));
        } catch (UnableToFillException e) {
            fail();
        }

        assertTrue(player.isActivable());


        //test2
        ProductionPowerExt power1 = new ProductionPowerExt(0, new NumberOfResources(1, 2, 3, 4), new NumberOfResources(1, 0, 0, 0), 1, 0);
        player.setToActive(power1);

        assertEquals(power1, player.getToActive());
        assertFalse(player.isActivable());

        try {
            player.getDepots().addResourcesFromMarket(new NumberOfResources(0, 2, 0, 0));
        } catch (UnableToFillException e) {
            fail();
        }

        assertTrue(player.isActivable());
    }

    @Test
    void errorMessage(){
        PlayerExt player = new PlayerExt("Pippo");
        assertEquals(ErrorMessage.NoError, player.getErrorMessage());

        player.setErrorMessage(ErrorMessage.MoveNotAllowed);
        assertEquals(ErrorMessage.MoveNotAllowed, player.getErrorMessage());


        player.setErrorMessage(ErrorMessage.CardNotOwned);
        assertEquals(ErrorMessage.CardNotOwned, player.getErrorMessage());

    }
}
