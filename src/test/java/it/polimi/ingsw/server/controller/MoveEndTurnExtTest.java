package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.DashboardExt;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.MarketExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveEndTurnExtTest {
    private static ArrayList<PlayerExt> players= new ArrayList<>();
    private static GameExt game;

    static {
        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        try {
            game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    public void CanPerform(){
        game.setIndex(0);
        game.setStatus(GameStatus.Initial);
        MoveEndTurnExt move= new MoveEndTurnExt(game.getCurrPlayer().getID());
        game.getCurrPlayer().setStatus(PlayerStatus.Active);

        assertFalse(move.canPerformExt(game));
        game.getCurrPlayer().setStatus(PlayerStatus.MovePerformed);
        assertFalse(move.canPerformExt(game));
        game.setStatus(GameStatus.Running);
        assertTrue(move.canPerformExt(game));
    }

    @Test
    public void PerformMove(){
        game.setIndex(0);
        game.setStatus(GameStatus.Running);
        MoveEndTurnExt move= new MoveEndTurnExt(game.getCurrPlayer().getID());
        game.getCurrPlayer().setStatus(PlayerStatus.Active);

        PlayerExt playerExt= game.getCurrPlayer();
        move.performMove(game);
        assertNotEquals(playerExt, game.getCurrPlayer());
        assertEquals(GameStatus.Running, game.getStatus());
        assertEquals(PlayerStatus.Active, game.getCurrPlayer().getStatus());
        assertEquals(PlayerStatus.Waiting, playerExt.getStatus());
    }
}
