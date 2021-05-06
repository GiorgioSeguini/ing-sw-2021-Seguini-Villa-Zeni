package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void Constructor() throws IOException{
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

        PersonalBoard personalBoard = new PersonalBoard(0);
        personalBoard.addLeaderCard(leaderCardsOw);

        Player player1 = new Player("Fabio1");
        assertNotNull(player1.getUserName());
        assertNotNull(player1.getPersonalBoard());
        assertNotNull(player1.getDepots());
        assertNotNull(player1.getDepots().getResources());
        assertNotNull(player1.getFaithTrack());
        assertNotNull(player1.getConverter());
        assertNotNull(player1.getDiscount());
        assertEquals(0,player1.getFaithTrack().getFaithPoints());
        assertTrue(player1.getVictoryPoints()>=0);
        assertEquals(0, player1.getDepots().getResources().size());
        assertEquals(PlayerStatus.Waiting, player1.getStatus());
        assertEquals(ErrorMessage.NoError, player1.getErrorMessage());
        assertNull(player1.getToActive());
        assertFalse(player1.isActivable());

    }

    @Test
    void GetterVictoryPoints() throws IOException, NoMoreLeaderCardAliveException {
        Player player= new Player("Fabio");
        assertTrue(player.getVictoryPoints()==0);
        player.getDepots().addResourceFromProduction(new NumberOfResources(0,0,5,0));
        assertFalse(player.getVictoryPoints()==0);
        ArrayList<LeaderCard> leaderCards;
        leaderCards = Starter.LeaderCardsParser();
        LeaderCard leaderCard1;
        LeaderCard leaderCard2;
        leaderCard1 = leaderCards.get(0);
        leaderCard2 = leaderCards.get(1);
        LeaderCard[] leaderCardsOw = new LeaderCard[] {leaderCard1,leaderCard2};

        player.getPersonalBoard().addLeaderCard(leaderCardsOw);

        LeaderCard[] leaderCardsOw2 = new LeaderCard[2];
        int i=0;
        for (LeaderCard x: player.getPersonalBoard().getLeaderCards()) {
            leaderCardsOw2[i] = x;
            assertNotNull(leaderCardsOw2[i]);
            i++;
        }

        player.getPersonalBoard().getLeaderCards()[0].setPlayed(player);
        assertTrue(player.getVictoryPoints()>0);
    }

    @Test
    void discountTest(){
        Player player = new Player("Pippo");

        assertEquals(new NumberOfResources(), player.getDiscount());

        try{
            player.addDiscount(ResourceType.Coins, -1);
            fail();
        }catch(IllegalArgumentException ignored){}

        player.addDiscount(ResourceType.Coins, 1);

        assertEquals(new NumberOfResources(0, 0, 1, 0), player.getDiscount());
    }

    @Test
    void productionTest(){
        Player player = new Player("Pippo");

        assertNull(player.getToActive());

        //test1
        ProductionPower power = new ProductionPower(0, new NumberOfResources(1, 2, 3, 4), new NumberOfResources(1, 0, 0, 0));
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
        ProductionPower power1 = new ProductionPower(0, new NumberOfResources(1, 2, 3, 4), new NumberOfResources(1, 0, 0, 0), 1, 0);
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
        Player player = new Player("Pippo");
        assertEquals(ErrorMessage.NoError, player.getErrorMessage());

        player.setErrorMessage(ErrorMessage.MoveNotAllowed);
        assertEquals(ErrorMessage.MoveNotAllowed, player.getErrorMessage());


        player.setErrorMessage(ErrorMessage.CardNotOwned);
        assertEquals(ErrorMessage.CardNotOwned, player.getErrorMessage());

    }
}
