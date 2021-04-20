package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Starter;
import it.polimi.ingsw.model.exception.NoMoreLeaderCardAliveException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void Constructor() throws IOException, ParseException, NoMoreLeaderCardAliveException {
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

    }

    @Test
    void GetterVictoryPoints() throws IOException, ParseException, NoMoreLeaderCardAliveException {
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
}
