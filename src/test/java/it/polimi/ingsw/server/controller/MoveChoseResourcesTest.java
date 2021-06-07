package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveChoseResourcesTest {
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
    public void CanPerform() throws UnableToFillException, NoSpaceException {
        game.setStatus(GameStatus.Running);
        game.setIndex(0);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        DevelopmentCardExt cardtobuy= game.getDashboard().getTopDevCard(ColorDevCard.BLUE, Level.ONE);

        MoveChoseResourcesExt move= new MoveChoseResourcesExt(game.getCurrPlayer().getID(),cardtobuy.getProductionPower().getInputRes(),cardtobuy.getProductionPower().getOutputRes());

        assertFalse(move.canPerformExt(game));//incorrect player status
        game.getCurrPlayer().setStatus(PlayerStatus.NeedToChoseRes);

        assertTrue(move.canPerformExt(game));
    }
}
