package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.DashboardExt;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.MarketExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class MoveChoseInitialResourcesExtTest {
    private static final ArrayList<PlayerExt> players= new ArrayList<>();
    private static final GameExt game;

    static {
        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        PlayerExt player3 = new PlayerExt("paperino");
        PlayerExt player4= new PlayerExt("topolino");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
    }

    @Test
    public void CanPerform(){
        game.setStatus(GameStatus.Initial);
        game.setIndex(0);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        MoveChoseInitialResourcesExt move= new MoveChoseInitialResourcesExt(game.getCurrPlayer().getID());
        move.setResources(new NumberOfResources(1,1,1,1));
        move.canPerform(game);
        assertFalse(move.canPerformExt(game));
        move.setResources(new NumberOfResources());
        assertFalse(move.canPerformExt(game));
        game.getCurrPlayer().setStatus(PlayerStatus.NeedToChoseRes);
        assertFalse(move.canPerformExt(game));

        game.getCurrPlayer().setStatus(PlayerStatus.Waiting);
        game.setIndex(1);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        move= new MoveChoseInitialResourcesExt(game.getCurrPlayer().getID());
        move.setResources(new NumberOfResources(1,0,0,0));
        assertTrue(move.canPerformExt(game));
        move.setResources(new NumberOfResources(1,1,1,0));
        assertFalse(move.canPerformExt(game));
        game.getCurrPlayer().setStatus(PlayerStatus.Waiting);
        game.setIndex(3);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        move= new MoveChoseInitialResourcesExt(game.getCurrPlayer().getID());
        move.setResources(new NumberOfResources(1,1,0,0));
        assertTrue(move.canPerformExt(game));

        game.setStatus(GameStatus.Running);
        assertFalse(move.canPerformExt(game));
    }

    @Test
    public void PerformMove(){
        game.setStatus(GameStatus.Initial);
        game.getCurrPlayer().setStatus(PlayerStatus.Waiting);
        game.setIndex(0);

        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        MoveChoseInitialResourcesExt move= new MoveChoseInitialResourcesExt(game.getCurrPlayer().getID());
        move.setResources(new NumberOfResources(0,0,0,1));

        if(move.canPerformExt(game)){
            move.performMove(game);
            fail();
        }
        assertEquals(new NumberOfResources(), game.getCurrPlayer().getDepots().getResources());

        game.getCurrPlayer().setStatus(PlayerStatus.Waiting);
        game.setIndex(1);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        move= new MoveChoseInitialResourcesExt(game.getCurrPlayer().getID());
        move.setResources(new NumberOfResources(1,1,1,1));

        if (move.canPerformExt(game)){
            move.performMove(game);
            fail();
        }
        assertEquals(new NumberOfResources(), game.getCurrPlayer().getDepots().getResources());

        move.setResources(new NumberOfResources(1,0,0,0));
        if (move.canPerformExt(game)){
            move.performMove(game);
        }
        else {
            fail();
        }
        assertEquals(move.getResources(), game.getCurrPlayer().getDepots().getResources());
    }

    @Test
    public void GetClassNameTest(){
        MoveChoseInitialResourcesExt moveChoseInitialResourcesExt = new MoveChoseInitialResourcesExt(game.getCurrPlayer().getID());
        assertNotNull(moveChoseInitialResourcesExt.getClassName());
        assertEquals("MoveChoseInitialResources",moveChoseInitialResourcesExt.getClassName());
    }
}
