package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
class GameTest {

    @Test
    void multiplayer() {
        ArrayList<PlayerExt> due = new ArrayList<>();
        due.add(new PlayerExt("Pippo"));
        due.add(new PlayerExt("Piero"));


        ArrayList<LeaderCardExt> leaderCards = new ArrayList<>();
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 1));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 2));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 3));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 4));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 5));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 6));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 7));

        MarketExt market = new MarketExt(Starter.MarblesParser());
        DashboardExt dashboard = new DashboardExt(new ArrayList<DevelopmentCardExt>());

        GameExt game = new GameExt(due, market, dashboard, new ArrayList<SoloActionTokens>(), leaderCards);
        System.out.println(game);
        assertEquals(market, game.getMarketTray());
        assertEquals(dashboard, game.getDashboard());

        //Check initial status
        assertEquals(GameStatus.Initial, game.getStatus());
        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());

        assertEquals(0, game.getCurrIndex());

        //check players
        assertEquals(2, game.getPlayers().size());
        PlayerExt player1 = game.getPlayer(0);
        assertTrue(due.contains(player1));
        assertEquals(0, game.getInitialResources(player1.getID()));
        assertEquals(0, game.getPlayerIndex(player1));
        PlayerExt player2 = game.getPlayer(1);
        assertTrue(due.contains(player2));
        assertEquals(1, game.getInitialResources(player2.getID()));
        assertEquals(1, game.getPlayerIndex(player2));

        //check fake player
        PlayerExt fakePlayerExt = new PlayerExt("Fake");
        assertEquals(-1, game.getPlayerIndex(fakePlayerExt));
        /*try{
            game.getInitialResources(fakePlayerExt.getID());
            fail();
        }catch(IllegalArgumentException ignored){}*/
        try{
            game.getActivableLeadCard(fakePlayerExt);
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
        LeaderCardExt[] chosen1 = new LeaderCardExt[]{(LeaderCardExt) game.getActivableLeadCard(player1).get(0), (LeaderCardExt) game.getActivableLeadCard(player1).get(3)};
        LeaderCardExt[] chosen2 = new LeaderCardExt[]{(LeaderCardExt) game.getActivableLeadCard(player2).get(0), (LeaderCardExt) game.getActivableLeadCard(player2).get(3)};
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
        System.out.println(game);
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
    void illegalInitialization() {
        MarketExt market = new MarketExt(Starter.MarblesParser());
        try{
            new GameExt(new ArrayList<>(), market, new DashboardExt(new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
            fail();
        }catch(IllegalArgumentException ignored){}
        ArrayList<PlayerExt> player = new ArrayList<>();
        player.add(new PlayerExt("Pippo"));
        try{
            new GameExt(player, market, new DashboardExt(new ArrayList<>()), new ArrayList<>(), new ArrayList<>());
            fail();
        }catch(IllegalArgumentException ignored){}

    }

    @Test
    void singlePlayerExt() {
        ArrayList<SoloActionTokens> tokens = new ArrayList<>();
        tokens.add(new Move2());
        tokens.add(new MoveShuffle());
        tokens.add(new Discard2(ColorDevCard.BLUE));
        //just three tokens, to win I'm sure it should perform at least one time a MoveShuffle and two Discard2


        assertEquals(ColorDevCard.BLUE, (new Discard2(ColorDevCard.BLUE)).getColor());
        ArrayList<PlayerExt> single = new ArrayList<>();
        single.add(new PlayerExt("Pippo"));

        ArrayList<DevelopmentCardExt> card = new ArrayList<>();
        card.add(new DevelopmentCardExt(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPowerExt(), 0));
        card.add(new DevelopmentCardExt(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPowerExt(), 1));
        card.add(new DevelopmentCardExt(Level.TWO, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPowerExt(), 2));
        card.add(new DevelopmentCardExt(Level.THREE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPowerExt(), 0));
        card.add(new DevelopmentCardExt(Level.ONE, ColorDevCard.YELLOW, new NumberOfResources(), new ProductionPowerExt(), 0));
        card.add(new DevelopmentCardExt(Level.TWO, ColorDevCard.PURPLE, new NumberOfResources(), new ProductionPowerExt(), 0));
        card.add(new DevelopmentCardExt(Level.THREE, ColorDevCard.GREEN, new NumberOfResources(), new ProductionPowerExt(), 0));

        ArrayList<LeaderCardExt> leaderCards = new ArrayList<>();
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0));

        MarketExt market = new MarketExt(Starter.MarblesParser());

        GameExt game = new GameExt(single, market, new DashboardExt(card), tokens, leaderCards);
        assertNotNull(game.getSoloGame());

        PlayerExt player = game.getPlayer(0);
        assertEquals("Pippo", player.getUserName());
        assertEquals(GameStatus.Initial, game.getStatus());
        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());
        LeaderCardExt[] chosen = new LeaderCardExt[]{ leaderCards.get(0), leaderCards.get(3)};
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
    void findLeaderCard() {
        ArrayList<LeaderCardExt> leaderCards= Starter.LeaderCardsParser();
        ArrayList<DevelopmentCardExt> developmentCards= Starter.DevCardParser();
        ArrayList<MarbleColor> marbles= Starter.MarblesParser();
        ArrayList<PlayerExt> players= new ArrayList<>();
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();
        DashboardExt dashboard= new DashboardExt(developmentCards);
        MarketExt market= new MarketExt(marbles);
        players.add(new PlayerExt("pippo"));
        tokens.add(new Discard2(ColorDevCard.BLUE));


        GameExt game= new GameExt(players,market,dashboard,tokens,leaderCards);
        Random rand= new Random();
        int id;

        for (int i=0; i<100; i++){
            id=rand.nextInt(100)+17;
            assertNull(game.findLeaderCard(id));
        }

        id=3;

    }
}