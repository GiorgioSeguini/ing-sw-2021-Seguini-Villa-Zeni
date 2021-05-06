package it.polimi.ingsw.server.model;

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
    void constructor() throws IOException{
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCard> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<Player> playerArrayList = new ArrayList<>();
        playerArrayList.add(new Player("Fabio"));

        Game game = new Game(playerArrayList, new Market(Starter.MarblesParser()), new Dashboard(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

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
    void Setter() throws IOException{
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCard> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<Player> playerArrayList = new ArrayList<>();
        playerArrayList.add(new Player("Fabio"));

        Game game = new Game(playerArrayList, new Market(Starter.MarblesParser()), new Dashboard(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

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
    void RevealTokenTest() throws IOException{
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCard> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<Player> playerArrayList = new ArrayList<>();
        playerArrayList.add(new Player("Fabio"));

        Game game = new Game(playerArrayList, new Market(Starter.MarblesParser()), new Dashboard(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

        int dim = game.getSoloGame().getSoloActionTokens().size();
        SoloActionTokens NextRevealedToken = game.getSoloGame().getSoloActionTokens().get(0);
        game.getSoloGame().revealToken();
        ArrayList <SoloActionTokens> temp = game.getSoloGame().getSoloActionTokens();
        //assertFalse(temp.contains(NextRevealedToken));                                  //a volte va, a volte no, Boh? TODO
        //assertEquals(--dim,game.getSoloGame().getSoloActionTokens().size());

    }

    @Test
    void singlePlayerGame() throws IOException{
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

        game.updateStatus();
        assertEquals(GameStatus.Initial, game.getStatus());
        LeaderCard[] chosen = new LeaderCard[]{ leaderCards.get(0), leaderCards.get(3)};
        game.getPlayer(0).getPersonalBoard().addLeaderCard(chosen);

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
