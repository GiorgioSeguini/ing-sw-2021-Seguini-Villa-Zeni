package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Starter;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoSoloPlayerTest {

    @Test
    void constructor() throws IOException, ParseException {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
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

        Player player = new Player("Fabio",personalBoard,0,new NumberOfResources());
        player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<Player> playerArrayList = new ArrayList<>(1);
        playerArrayList.add(player);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        startMarbles = Starter.MarblesParser();
        Market marketTray = new Market(startMarbles);

        ArrayList<DevelopmentCard> developmentCardArrayList;
        developmentCardArrayList = Starter.DevCardParser();
        Dashboard dashboard = new Dashboard(developmentCardArrayList);

        Game game = new Game(playerArrayList,marketTray,dashboard,soloActionTokensArrayList);

        assertNotNull(game.getSoloGame().getGame());
        assertNotNull(game.getSoloGame().getFaithTrack());
        assertEquals(0,game.getSoloGame().getFaithTrack().getFaithPoints());
        assertNotNull(game.getSoloGame().getSoloActionTokens());
        assertNotNull(game.getSoloGame().getCopyOfSoloActionTokensInit());
        assertEquals(7,game.getSoloGame().getSoloActionTokens().size());
        assertEquals(7,game.getSoloGame().getCopyOfSoloActionTokensInit().size());

        for(SoloActionTokens x: game.getSoloGame().getSoloActionTokens()){
            assertTrue(soloActionTokensArrayList.contains(x));
        }
        for (SoloActionTokens x: game.getSoloGame().getCopyOfSoloActionTokensInit()){
            assertTrue(soloActionTokensArrayList.contains(x));
        }
    }

    @Test
    void Setter() throws IOException, ParseException {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;
        soloActionTokensArrayList = Starter.TokensParser();

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

        Player player = new Player("Fabio",personalBoard,0,new NumberOfResources());
        player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<Player> playerArrayList = new ArrayList<>(1);
        playerArrayList.add(player);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        startMarbles = Starter.MarblesParser();
        Market marketTray = new Market(startMarbles);

        ArrayList<DevelopmentCard> developmentCardArrayList;
        developmentCardArrayList = Starter.DevCardParser();
        Dashboard dashboard = new Dashboard(developmentCardArrayList);

        Game game = new Game(playerArrayList,marketTray,dashboard,soloActionTokensArrayList);

        Collections.shuffle(soloActionTokensArrayList);
        game.getSoloGame().setSoloActionTokens(soloActionTokensArrayList);
        assertNotNull(game.getSoloGame().getSoloActionTokens());
        for (SoloActionTokens x: soloActionTokensArrayList){
            assertNotNull(x);
        }
        for (SoloActionTokens x: game.getSoloGame().getSoloActionTokens()){
            assertTrue(soloActionTokensArrayList.contains(x));
        }

    }

    @Test
    void RevealTokenTest() throws IOException, ParseException {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;
        soloActionTokensArrayList = Starter.TokensParser();

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

        Player player = new Player("Fabio",personalBoard,0,new NumberOfResources());
        player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<Player> playerArrayList = new ArrayList<>(1);
        playerArrayList.add(player);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        startMarbles = Starter.MarblesParser();
        Market marketTray = new Market(startMarbles);

        ArrayList<DevelopmentCard> developmentCardArrayList;
        developmentCardArrayList = Starter.DevCardParser();
        Dashboard dashboard = new Dashboard(developmentCardArrayList);

        Game game = new Game(playerArrayList,marketTray,dashboard,soloActionTokensArrayList);

        int dim = game.getSoloGame().getSoloActionTokens().size();
        SoloActionTokens NextRevealedToken = game.getSoloGame().getSoloActionTokens().get(0);
        game.getSoloGame().revealToken();
        assertFalse(game.getSoloGame().getSoloActionTokens().contains(NextRevealedToken));
        assertEquals(--dim,game.getSoloGame().getSoloActionTokens().size());

    }
}
