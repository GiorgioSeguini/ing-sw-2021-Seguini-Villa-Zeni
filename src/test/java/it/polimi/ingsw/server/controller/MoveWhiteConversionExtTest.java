package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class MoveWhiteConversionExtTest {

    @Test
    public void CanPerformMoveTest() {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;

        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
        game.setStatus(GameStatus.Running);

        MoveWhiteConversionExt moveWhiteConversionExt = new MoveWhiteConversionExt(game.getCurrPlayer().getID());
        assertFalse(moveWhiteConversionExt.canPerformExt(game));
        assertEquals(ErrorMessage.MoveNotAllowed, game.getCurrPlayer().getErrorMessage());

        game.getCurrPlayer().setStatus(PlayerStatus.NeedToConvert);
        game.getCurrPlayer().getConverter().setResources(new NumberOfResources());
        moveWhiteConversionExt = new MoveWhiteConversionExt(game.getCurrPlayer().getID());
        moveWhiteConversionExt.setWhiteMarbles(new ArrayList<>());
        assertTrue(moveWhiteConversionExt.canPerformExt(game));

        game.getCurrPlayer().setStatus(PlayerStatus.NeedToConvert);
        game.getCurrPlayer().getConverter().setWhite(2);
        moveWhiteConversionExt = new MoveWhiteConversionExt(game.getCurrPlayer().getID());
        moveWhiteConversionExt.setWhiteMarbles(new ArrayList<>());
        assertFalse(moveWhiteConversionExt.canPerformExt(game));
        assertEquals(ErrorMessage.BadChoice, game.getCurrPlayer().getErrorMessage());
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

        MoveWhiteConversionExt moveWhiteConversionExt = new MoveWhiteConversionExt(game.getCurrPlayer().getID());
        assertNotNull(moveWhiteConversionExt.getClassName());
        assertEquals("MoveWhiteConversion", moveWhiteConversionExt.getClassName());
    }
}
