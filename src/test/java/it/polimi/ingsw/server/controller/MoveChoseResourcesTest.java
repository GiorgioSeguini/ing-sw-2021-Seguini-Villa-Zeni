package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveChoseResourcesTest {
    private static final ArrayList<PlayerExt> players= new ArrayList<>();
    private static final GameExt game;

    static {
        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
    }

    @Test
    public void CanPerform() {
        game.setStatus(GameStatus.Running);
        game.setIndex(0);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        DevelopmentCardExt cardtobuy= game.getDashboard().getTopDevCard(ColorDevCard.BLUE, Level.ONE);

        MoveChoseResourcesExt move= new MoveChoseResourcesExt(game.getCurrPlayer().getID(),cardtobuy.getProductionPower().getInputRes(),cardtobuy.getProductionPower().getOutputRes());

        assertFalse(move.canPerformExt(game));//incorrect player status
        game.getCurrPlayer().setStatus(PlayerStatus.NeedToChoseRes);

        assertTrue(move.canPerformExt(game));
    }

    @Test
    public void PerformMoveTest() {

        game.setStatus(GameStatus.Running);
        game.getCurrPlayer().setToActive(new ProductionPowerExt());
        MoveChoseResourcesExt moveChoseResourcesExt = new MoveChoseResourcesExt(game.getCurrPlayer().getID(), new NumberOfResources(2,0,0,0), new NumberOfResources(0,0,0,0));
        moveChoseResourcesExt.performMove(game);
        assertEquals(ErrorMessage.ChooseResourceError,game.getCurrPlayer().getErrorMessage());
        ArrayList<PlayerExt> players2= new ArrayList<>();
        GameExt game2;
        PlayerExt player12= new PlayerExt("pippo");
        PlayerExt player22= new PlayerExt("pluto");
        players2.add(player12);
        players2.add(player22);
        game2 = new GameExt(players2, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
        game2.setStatus(GameStatus.Running);
        game2.getCurrPlayer().setToActive(new ProductionPowerExt(0,new NumberOfResources(0,0,0,0),new NumberOfResources(0,0,0,0),2,1));
        MoveChoseResourcesExt moveChoseResourcesExt2 = new MoveChoseResourcesExt(game2.getCurrPlayer().getID(), new NumberOfResources(2,0,0,0), new NumberOfResources(0,1,0,0));
        moveChoseResourcesExt2.performMove(game2);
        assertEquals(ErrorMessage.OutOfResourcesError,game2.getCurrPlayer().getErrorMessage());
    }

    @Test
    public void GetClassNameTest() {

        NumberOfResources input = new NumberOfResources(1,1,0,0);
        NumberOfResources output = new NumberOfResources(1,0,0,0);
        MoveChoseResourcesExt moveChoseResourcesExt = new MoveChoseResourcesExt(game.getCurrPlayer().getID(),input, output);
        assertNotNull(moveChoseResourcesExt.getClassName());
        assertEquals("MoveChoseResources",moveChoseResourcesExt.getClassName());
        assertEquals(input,moveChoseResourcesExt.getOfYourChoiceInput());
        assertEquals(output,moveChoseResourcesExt.getOfYourChoiceOutput());
    }
}
