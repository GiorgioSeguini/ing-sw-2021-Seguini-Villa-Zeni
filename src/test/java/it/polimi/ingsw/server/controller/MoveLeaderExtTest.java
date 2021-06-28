package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class MoveLeaderExtTest {
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
    public void ConstructorTest(){
        int idPlayer=0;
        MoveLeaderExt moveLeaderExt = new MoveLeaderExt(idPlayer);
        assertNotNull(moveLeaderExt);
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

        LeaderCardExt leaderCardExt1 = new LeaderCardExt(new RequirementsExt(), new DepotsAbility(ResourceType.Coins), 0);
        LeaderCardExt leaderCardExt2 = new LeaderCardExt(new RequirementsExt(), new DepotsAbility(ResourceType.Coins), 0);
        game.getCurrPlayer().getPersonalBoard().addLeaderCard(new LeaderCardExt[] {leaderCardExt1,leaderCardExt2});
        //game isn't start yet
        MoveLeaderExt moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setIdLeaderCard(game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(0).getId());
        moveLeaderExt.setMove(0);
        assertFalse(moveLeaderExt.canPerformExt(game));
        //right working
        game.setStatus(GameStatus.Running);
        game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(1).setStatus(LeaderStatus.Dead);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setIdLeaderCard(game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(0).getId());
        moveLeaderExt.setMove(0);
        assertTrue(moveLeaderExt.canPerformExt(game));
        //card not owned
        moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setMove(0);
        moveLeaderExt.setIdLeaderCard(3);
        assertFalse(moveLeaderExt.canPerformExt(game));
    }

    @Test
    public void PerformMoveTest() {
        LeaderCardExt leaderCardExt1 = Starter.LeaderCardsParser().get(4);
        LeaderCardExt leaderCardExt2 = Starter.LeaderCardsParser().get(5);
        game.getCurrPlayer().getPersonalBoard().addLeaderCard(new LeaderCardExt[]{leaderCardExt1,leaderCardExt2});
        game.setStatus(GameStatus.Running);
        game.getCurrPlayer().setStatus(PlayerStatus.Active);
        game.getCurrPlayer().getDepots().addResourceFromProduction(new NumberOfResources(10,10,10,10));
        //right work on move 0
        MoveLeaderExt moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setMove(0);
        moveLeaderExt.setIdLeaderCard(game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(0).getId());
        moveLeaderExt.performMove(game);
        assertEquals(ErrorMessage.NoError, game.getCurrPlayer().getErrorMessage());
        //trying to play a card already played
        moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setMove(0);
        moveLeaderExt.setIdLeaderCard(game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(0).getId());
        moveLeaderExt.performMove(game);
        assertEquals(ErrorMessage.BadChoice, game.getCurrPlayer().getErrorMessage());
        //right work on move 1
        moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setMove(1);
        moveLeaderExt.setIdLeaderCard(game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(1).getId());
        moveLeaderExt.performMove(game);
        assertEquals(ErrorMessage.NoError, game.getCurrPlayer().getErrorMessage());
        //trying to discard a card not on hand
        moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setMove(1);
        moveLeaderExt.setIdLeaderCard(game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(0).getId());
        moveLeaderExt.performMove(game);
        assertEquals(ErrorMessage.BadChoice, game.getCurrPlayer().getErrorMessage());
    }

    @Test
    public void GetClassNameTest(){
        MoveLeaderExt moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        assertNotNull(moveLeaderExt.getClassName());
        assertEquals("MoveLeader",moveLeaderExt.getClassName());
    }
}
