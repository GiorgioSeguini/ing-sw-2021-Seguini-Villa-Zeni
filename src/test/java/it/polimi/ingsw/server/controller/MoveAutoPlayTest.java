package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class MoveAutoPlayTest {

    @Test
    public void ConstructorTest() {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;

        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        MoveAutoPlay moveAutoPlay = new MoveAutoPlay(game.getCurrPlayer().getID());
        assertNotNull(moveAutoPlay);

        assertEquals(game.getCurrPlayer().getID(), moveAutoPlay.getIdPlayer());
    }

    @Test
    public void CanPerformExtTest() {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;

        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
        MoveAutoPlay moveAutoPlay = new MoveAutoPlay(game.getCurrPlayer().getID());
        assertTrue(moveAutoPlay.canPerformExt(game));
    }

    @Test
    public void PerformMoveExtTest() throws UnableToFillException {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;

        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game = new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
        //Chose LeaderCard and initial resources
        MoveAutoPlay moveAutoPlay = new MoveAutoPlay(game.getCurrPlayer().getID());
        moveAutoPlay.performMove(game);
        assertTrue(game.getPlayer(0).getPersonalBoard().isReady());
        MoveAutoPlay moveAutoPlay2 = new MoveAutoPlay(game.getCurrPlayer().getID());
        moveAutoPlay2.performMove(game);
        assertTrue(game.getPlayer(1).getPersonalBoard().isReady());
        assertNotEquals(game.getPlayer(1).getDepots().getResources(), new NumberOfResources());
        assertNotEquals(GameStatus.Initial, game.getStatus());
        assertSame(game.getStatus(), GameStatus.Running);
        assertSame(game.getCurrPlayer(), player1);
        //NextTurn
        MoveAutoPlay moveAutoPlay3 = new MoveAutoPlay(game.getCurrPlayer().getID());
        moveAutoPlay3.performMove(game);
        assertSame(game.getCurrPlayer(), player2);
        //needToChose res
        player2.setStatus(PlayerStatus.NeedToChoseRes);

        game.getCurrPlayer().getDepots().addResourcesFromMarket(new NumberOfResources(0,2,1,0));
        NumberOfResources numberOfResourcesPRE = game.getCurrPlayer().getDepots().getResources();
        game.getCurrPlayer().setToActive(new ProductionPowerExt(0,new NumberOfResources(),new NumberOfResources(),2,1));
        MoveAutoPlay moveAutoPlay4 = new MoveAutoPlay(game.getCurrPlayer().getID());
        moveAutoPlay4.performMove(game);
        assertNotEquals(numberOfResourcesPRE, game.getPlayer(1).getDepots().getResources());
        //NeedToConvert
        ArrayList<PlayerExt> players2= new ArrayList<>();
        GameExt game2;

        PlayerExt player12= new PlayerExt("pippo");
        PlayerExt player22= new PlayerExt("pluto");
        players2.add(player12);
        players2.add(player22);
        game2 = new GameExt(players2, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
        game2.getCurrPlayer().setStatus(PlayerStatus.Active);
        game2.setStatus(GameStatus.Running);
        LeaderCardExt leaderCardExt = new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Servants),0);
        LeaderCardExt leaderCardExt2 = new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Stones),0);
        LeaderCardExt[] leaderCardExts = new LeaderCardExt[] {leaderCardExt,leaderCardExt2};
        game2.getPlayer(0).getPersonalBoard().addLeaderCard(leaderCardExts);
        leaderCardExt.setPlayed(game2.getCurrPlayer());
        leaderCardExt2.setPlayed(game2.getCurrPlayer());
        game2.getCurrPlayer().getConverter().setWhite(3);
        game2.getCurrPlayer().setStatus(PlayerStatus.NeedToConvert);
        MoveAutoPlay moveAutoPlay5 = new MoveAutoPlay(game2.getCurrPlayer().getID());
        moveAutoPlay5.performMove(game2);
        //assertEquals(new NumberOfResources(3,0,0,0), game2.getCurrPlayer().getDepots().getResources());
        //NeedToStore
        System.out.println(game.getCurrPlayer());
        game.getCurrPlayer().setStatus(PlayerStatus.NeedToStore);
        NumberOfResources numberOfResources = game.getCurrPlayer().getDepots().getResources();
        game.getCurrPlayer().getConverter().setResources(new NumberOfResources(3,0,0,0));
        MoveAutoPlay moveAutoPlay6 = new MoveAutoPlay(game.getCurrPlayer().getID());
        moveAutoPlay6.performMove(game);
        assertEquals(numberOfResources,game.getPlayer(0).getDepots().getResources());

     }
}
