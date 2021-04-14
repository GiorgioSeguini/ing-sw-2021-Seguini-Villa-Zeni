package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Starter;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void Constructor() throws IOException, ParseException {
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

        PersonalBoard personalBoard = new PersonalBoard(leaderCardsOw);

        Player player1 = new Player("Fabio1",personalBoard,0,new NumberOfResources(0,0,0,0));
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

        Player player2 = new Player("Fabio2", personalBoard, 0,new NumberOfResources(1,0,0,0));
        assertEquals(1, player2.getDepots().getResources().size());

        Player player3 = new Player("Fabio3", personalBoard, 1,new NumberOfResources(1,0,0,0));
        assertEquals(1, player3.getDepots().getResources().size());
        assertEquals(1, player3.getFaithTrack().getFaithPoints());

        Player player4 = new Player("Fabio4", personalBoard, 1,new NumberOfResources(1,1,0,0));
        assertEquals(2, player4.getDepots().getResources().size());
        assertEquals(1, player4.getFaithTrack().getFaithPoints());
    }
}
