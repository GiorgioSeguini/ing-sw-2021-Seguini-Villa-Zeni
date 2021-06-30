package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveDiscardResourcesExtTest {

    @Test
    public void CanPerformExtTest() {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;
        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        game.setStatus(GameStatus.Running);
        game.getCurrPlayer().setStatus(PlayerStatus.NeedToStore);
        MoveDiscardResourcesExt moveDiscardResourcesExt = new MoveDiscardResourcesExt(game.getCurrPlayer().getID());
        assertTrue(moveDiscardResourcesExt.canPerformExt(game));

        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        moveDiscardResourcesExt = new MoveDiscardResourcesExt(game.getCurrPlayer().getID());
        assertFalse(moveDiscardResourcesExt.canPerformExt(game));
        assertEquals(ErrorMessage.MoveNotAllowed,game.getCurrPlayer().getErrorMessage());
    }

    @Test
    public void PerformMoveTest() throws UnableToFillException {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;
        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        game.setStatus(GameStatus.Running);
        game.getCurrPlayer().getDepots().addResourcesFromMarket(new NumberOfResources(2,1,1,0));

        MoveDiscardResourcesExt moveDiscardResourcesExt = new MoveDiscardResourcesExt(game.getCurrPlayer().getID());
        moveDiscardResourcesExt.setToDiscard(new NumberOfResources(0,1,1,1));
        moveDiscardResourcesExt.performMove(game);
        assertEquals(ErrorMessage.OutOfResourcesError, game.getCurrPlayer().getErrorMessage());

        game.getCurrPlayer().getConverter().setResources(new NumberOfResources(0,1,1,1));
        moveDiscardResourcesExt = new MoveDiscardResourcesExt(game.getCurrPlayer().getID());
        moveDiscardResourcesExt.setToDiscard(new NumberOfResources(0,0,0,0));
        moveDiscardResourcesExt.performMove(game);
        assertEquals(ErrorMessage.UnableToFillError, game.getCurrPlayer().getErrorMessage());
    }

    @Test
    public void singleGAmeTest(){
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;
        PlayerExt player1= new PlayerExt("pippo");
        players.add(player1);

        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        game.setStatus(GameStatus.Running);
        game.getCurrPlayer().getConverter().setResources(new NumberOfResources(0,1,1,1));
        MoveDiscardResourcesExt moveDiscardResourcesExt = new MoveDiscardResourcesExt(game.getCurrPlayer().getID());
        moveDiscardResourcesExt.setToDiscard(new NumberOfResources(0,1,1,0));
        moveDiscardResourcesExt.performMove(game);
        assertEquals(2, game.getSoloGame().getFaithTrack().getFaithPoints());
    }

    @Test
    public void GetClassNameTest() {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;
        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        MoveDiscardResourcesExt moveDiscardResourcesExt = new MoveDiscardResourcesExt(game.getCurrPlayer().getID());
        assertNotNull(moveDiscardResourcesExt.getClassName());
        assertEquals("MoveDiscardResources", moveDiscardResourcesExt.getClassName());
    }
}
