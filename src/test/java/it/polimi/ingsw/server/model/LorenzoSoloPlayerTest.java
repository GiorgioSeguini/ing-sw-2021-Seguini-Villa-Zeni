package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoSoloPlayerTest {

    @Test
    void constructor() {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCardExt> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<PlayerExt> playerArrayList = new ArrayList<>();
        playerArrayList.add(new PlayerExt("Fabio"));

        GameExt game = new GameExt(playerArrayList, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

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
    void Setter() {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCardExt> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<PlayerExt> playerArrayList = new ArrayList<>();
        playerArrayList.add(new PlayerExt("Fabio"));

        GameExt game = new GameExt(playerArrayList, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

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
    void RevealTokenTest() {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCardExt> leaderCards;

        ArrayList<PlayerExt> playerArrayList = new ArrayList<>();
        playerArrayList.add(new PlayerExt("Fabio"));

        GameExt game = new GameExt(playerArrayList, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

        int dim = game.getSoloGame().getSoloActionTokens().size();
        SoloActionTokens NextRevealedToken = game.getSoloGame().getSoloActionTokens().get(0);
        game.getSoloGame().revealToken();
        ArrayList <SoloActionTokens> temp = game.getSoloGame().getSoloActionTokens();

    }

    @Test
    void singlePlayerExtGame() {
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

        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());
        LeaderCardExt[] chosen = new LeaderCardExt[]{ leaderCards.get(0), leaderCards.get(3)};
        ((PlayerExt)game.getPlayers().get(0)).getPersonalBoard().addLeaderCard(chosen);

        game.updateStatus();
        //now game is running
        assertEquals(GameStatus.Running, game.getStatus());

        while(!game.getSoloGame().isWinner()) {
            game.nextTurn();        //perform one move
        }

        assertEquals(GameStatus.Ended, game.getStatus());
        assertNull(game.getWinner());

    }
}
