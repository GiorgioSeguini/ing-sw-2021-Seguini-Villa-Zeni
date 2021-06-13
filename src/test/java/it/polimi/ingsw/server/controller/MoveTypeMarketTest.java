package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class MoveTypeMarketTest {
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
    public void ConstructorTest(){
        int idPlayer=0;
        MoveTypeMarketExt moveTypeMarketExt = new MoveTypeMarketExt(idPlayer);
        assertNotNull(moveTypeMarketExt);
    }

    /*@Test
    public void TakesMarblesTest(){
        int indexToBuy;
        ArrayList<MarbleColor> startMarbles = new ArrayList<>();

        try {
            startMarbles = Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        MarketExt marketExt = new MarketExt(startMarbles);
        int idPlayer=0;
        MoveTypeMarketExt moveTypeMarketExt = new MoveTypeMarketExt(idPlayer);
        indexToBuy = 0;
        ArrayList<MarbleColor> takenMarbles;
        takenMarbles = moveTypeMarketExt.
    }*/

    @Test
    public void CanPerformExtTest(){
        MoveTypeMarketExt marketExt = new MoveTypeMarketExt(game.getCurrPlayer().getID());
        assertFalse(marketExt.canPerformExt(game));
        assertEquals(ErrorMessage.MoveNotAllowed,game.getPlayerFromID(game.getCurrPlayer().getID()).getErrorMessage());
        game.setStatus(GameStatus.Running);
        game.getPlayer(0).setStatus(PlayerStatus.Active);
        assertTrue(marketExt.canPerformExt(game));
    }

    @Test
    public void PerformMoveExtTest() throws FileNotFoundException, NoSpaceException {
        MoveTypeMarketExt marketExt = new MoveTypeMarketExt(game.getCurrPlayer().getID());
        marketExt.performMove(game);
        assertEquals(ErrorMessage.NoError, game.getCurrPlayer().getErrorMessage());
        assertEquals(PlayerStatus.NeedToStore, game.getCurrPlayer().getStatus());
        //per entrare nel buycolumn di takesmarbles
        marketExt.setIndexToBuy(6);
        marketExt.performMove(game);
        //per avere badchoice
        marketExt.setIndexToBuy(7);
        marketExt.performMove(game);
        assertEquals(ErrorMessage.BadChoice,game.getCurrPlayer().getErrorMessage());

        LeaderCardExt leaderCardExt1 = Starter.LeaderCardsParser().get(0);
        LeaderCardExt leaderCardExt2 = Starter.LeaderCardsParser().get(10);
        game.getCurrPlayer().getPersonalBoard().addLeaderCard(new LeaderCardExt[]{leaderCardExt1,leaderCardExt2});
        game.getCurrPlayer().getPersonalBoard().getLeaderCards().get(1);
        DevelopmentCardExt developmentCardExt1 = Starter.DevCardParser().get(3);
        DevelopmentCardExt developmentCardExt2 = Starter.DevCardParser().get(2);
        DevelopmentCardExt developmentCardExt3 = Starter.DevCardParser().get(6);
        game.getCurrPlayer().getPersonalBoard().addDevCard(developmentCardExt1,0);
        game.getCurrPlayer().getPersonalBoard().addDevCard(developmentCardExt2,1);
        game.getCurrPlayer().getPersonalBoard().addDevCard(developmentCardExt3,2);
        MoveLeaderExt moveLeaderExt = new MoveLeaderExt(game.getCurrPlayer().getID());
        moveLeaderExt.setMove(0);
        moveLeaderExt.setIdLeaderCard(10);
        moveLeaderExt.performMove(game);
        marketExt.setIndexToBuy(2);
        marketExt.performMove(game);
        //assertEquals(PlayerStatus.NeedToConvert, game.getCurrPlayer().getStatus());
    }
}
