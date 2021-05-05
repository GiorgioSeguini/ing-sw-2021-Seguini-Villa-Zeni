package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void multiplayer() throws IOException, ParseException {
        ArrayList<Player> due = new ArrayList<>();
        due.add(new Player("Pippo"));
        due.add(new Player("Piero"));


        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 1));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 2));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 3));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 4));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 5));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 6));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 7));

        Market market = new Market(Starter.MarblesParser());
        Dashboard dashboard = new Dashboard(new ArrayList<DevelopmentCard>());

        Game game = new Game(due, market, dashboard, new ArrayList<SoloActionTokens>(), leaderCards);
        assertEquals(market, game.getMarketTray());
        assertEquals(dashboard, game.getDashboard());

        //Check initial status
        assertEquals(GameStatus.Initial, game.getStatus());
        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());
        assertNull(game.getCurrPlayer());
        assertEquals(0, game.getCurrIndex());

        //check players
        assertEquals(2, game.getPlayers().size());
        Player player1 = game.getPlayer(0);
        assertTrue(due.contains(player1));
        assertEquals(0, game.getInitialResources(player1));
        assertEquals(0, game.getPlayerIndex(player1));
        Player player2 = game.getPlayer(1);
        assertTrue(due.contains(player2));
        assertEquals(1, game.getInitialResources(player2));
        assertEquals(1, game.getPlayerIndex(player2));

        //check fake player
        Player fakePlayer = new Player("Fake");
        assertEquals(-1, game.getPlayerIndex(fakePlayer));
        try{
            game.getInitialResources(fakePlayer);
            fail();
        }catch(IllegalArgumentException ignored){}
        try{
            game.getActivableLeadCard(fakePlayer);
            fail();
        }catch(IllegalArgumentException ignored){}


        //check leader cards
        for(LeaderCard c : game.getActivableLeadCard(player1)){
            assertTrue(leaderCards.remove(c));
        }
        for(LeaderCard c : game.getActivableLeadCard(player2)){
            assertTrue(leaderCards.remove(c));
        }
        assertEquals(0, leaderCards.size());



        //chose leader cards
        LeaderCard[] chosen1 = new LeaderCard[]{ game.getActivableLeadCard(player1).get(0), game.getActivableLeadCard(player1).get(3)};
        LeaderCard[] chosen2 = new LeaderCard[]{ game.getActivableLeadCard(player2).get(0), game.getActivableLeadCard(player2).get(3)};
        player1.getPersonalBoard().addLeaderCard(chosen1);
        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());

        player2.getPersonalBoard().addLeaderCard(chosen2);
        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());

        try {
            player2.getDepots().addResourcesFromMarket(new NumberOfResources(1, 0, 0, 0));
        } catch (UnableToFillException e) {
            fail();
        }
        game.updateStatus();
        //now game is running
        assertEquals(GameStatus.Running, game.getStatus());
        assertEquals(player1, game.getCurrPlayer());
        game.nextTurn();
        assertEquals(player2, game.getCurrPlayer());
        game.nextTurn();
        assertEquals(player1, game.getCurrPlayer());

        for(int i=0; i<24; i++){
            player1.getFaithTrack().addPoint();
        }
        game.popesInspection();
        game.nextTurn();
        assertEquals(GameStatus.LastTurn, game.getStatus());
        try{
            game.getWinner();
            fail();
        }catch (IllegalArgumentException ignored){}

        game.nextTurn();
        assertEquals(GameStatus.Ended, game.getStatus());
        assertEquals(player1, game.getWinner());

    }

    @Test
    void illegalInitialization() throws IOException, ParseException {
        Market market = new Market(Starter.MarblesParser());
        try{
            new Game(new ArrayList<>(), market, new Dashboard(new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
            fail();
        }catch(IllegalArgumentException ignored){}
        ArrayList<Player> player = new ArrayList<>();
        player.add(new Player("Pippo"));
        try{
            new Game(player, market, new Dashboard(new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
            fail();
        }catch(IllegalArgumentException ignored){}

    }

    @Test
    void singlePlayer() throws IOException, ParseException {
        ArrayList<SoloActionTokens> tokens = new ArrayList<>();
        tokens.add(new Move2());
        tokens.add(new MoveShuffle());
        tokens.add(new Discard2(ColorDevCard.BLUE));
        //just three tokens, to win I'm sure it should perform at least one time a MoveShuffle and two Discard2


        assertEquals(ColorDevCard.BLUE, (new Discard2(ColorDevCard.BLUE)).getColor());
        ArrayList<Player> single = new ArrayList<>();
        single.add(new Player("Pippo"));

        ArrayList<DevelopmentCard> card = new ArrayList<>();
        card.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 0));
        card.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 1));
        card.add(new DevelopmentCard(Level.TWO, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 2));
        card.add(new DevelopmentCard(Level.THREE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 0));
        card.add(new DevelopmentCard(Level.ONE, ColorDevCard.YELLOW, new NumberOfResources(), new ProductionPower(), 0));
        card.add(new DevelopmentCard(Level.TWO, ColorDevCard.PURPLE, new NumberOfResources(), new ProductionPower(), 0));
        card.add(new DevelopmentCard(Level.THREE, ColorDevCard.GREEN, new NumberOfResources(), new ProductionPower(), 0));

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0));

        Market market = new Market(Starter.MarblesParser());

        Game game = new Game(single, market, new Dashboard(card), tokens, leaderCards);
        assertNotNull(game.getSoloGame());

        Player player = game.getPlayer(0);
        assertEquals("Pippo", player.getUserName());
        assertEquals(GameStatus.Initial, game.getStatus());
        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());
        LeaderCard[] chosen = new LeaderCard[]{ leaderCards.get(0), leaderCards.get(3)};
        game.getPlayer(0).getPersonalBoard().addLeaderCard(chosen);

        game.updateStatus();
        //now game is running
        assertEquals(GameStatus.Running, game.getStatus());

        //8 faith points for Lorenzo
        for(int i=0; i<8; i++)
            game.getSoloGame().getFaithTrack().addPoint();

        game.popesInspection();

        //24 faith points for player
        for(int i=0; i<24; i++)
            game.getCurrPlayer().getFaithTrack().addPoint();

        game.popesInspection();
        game.nextTurn();
        assertEquals(GameStatus.Ended, game.getStatus());

        assertEquals(player, game.getWinner());


    }

    @Test
    void findLeaderCard() throws IOException, ParseException {
        ArrayList<LeaderCard> leaderCards= Starter.LeaderCardsParser();
        ArrayList<DevelopmentCard> developmentCards= Starter.DevCardParser();
        ArrayList<MarbleColor> marbles= Starter.MarblesParser();
        ArrayList<Player> players= new ArrayList<>();
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();
        Dashboard dashboard= new Dashboard(developmentCards);
        Market market= new Market(marbles);
        players.add(new Player("pippo"));
        tokens.add(new Discard2(ColorDevCard.BLUE));


        Game game= new Game(players,market,dashboard,tokens,leaderCards);
        Random rand= new Random();
        int id;
        for (int i=1; i<17; i++){
            //id=rand.nextInt(15)+1;
            //assertNotNull(game.findLeaderCard(i));    TODO
        }

        for (int i=0; i<100; i++){
            id=rand.nextInt(100)+17;
            assertNull(game.findLeaderCard(id));
        }

        id=3;
        //assertEquals(dashboard.findDevCard(id).getColor(), ColorDevCard.BLUE);        TODO
        //assertEquals(dashboard.findDevCard(id).getLevel(), Level.ONE);            TODO
    }
}