package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class MoveTypeMarketTest {


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
    public void CanPerformExtTest() {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;

        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        MoveTypeMarketExt marketExt = new MoveTypeMarketExt(game.getCurrPlayer().getID());
        assertFalse(marketExt.canPerformExt(game));
        assertEquals(ErrorMessage.MoveNotAllowed,game.getPlayerFromID(game.getCurrPlayer().getID()).getErrorMessage());
        game.setStatus(GameStatus.Running);
        game.getPlayer(0).setStatus(PlayerStatus.Active);
        assertTrue(marketExt.canPerformExt(game));
    }

    @Test
    public void PerformMoveExtTest() throws NoSpaceException {
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;

        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        MoveTypeMarketExt marketExt = new MoveTypeMarketExt(game.getCurrPlayer().getID());
        marketExt.performMove(game);
        assertEquals(ErrorMessage.NoError, game.getCurrPlayer().getErrorMessage());
        assertEquals(PlayerStatus.NeedToStore, game.getCurrPlayer().getStatus());
        //per entrare nel buycolumn di takesmarbles
        MoveTypeMarketExt marketExt2 = new MoveTypeMarketExt(game.getCurrPlayer().getID());
        marketExt2.setIndexToBuy(6);
        marketExt2.performMove(game);
        //per avere badchoice
        MoveTypeMarketExt marketExt3 = new MoveTypeMarketExt(game.getCurrPlayer().getID());
        marketExt3.setIndexToBuy(7);
        marketExt3.performMove(game);
        assertEquals(ErrorMessage.BadChoice,game.getCurrPlayer().getErrorMessage());
        //creating a new game
        ArrayList<PlayerExt> players2= new ArrayList<>();
        GameExt game2;

        PlayerExt player12= new PlayerExt("pippo");
        PlayerExt player22= new PlayerExt("pluto");
        players2.add(player12);
        players2.add(player22);
        game2= new GameExt(players2, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());
        //testing needToConvert branch
        game2.getCurrPlayer().setStatus(PlayerStatus.Active);
        game2.setStatus(GameStatus.Running);
        LeaderCardExt leaderCardExt1 = new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0);
        LeaderCardExt leaderCardExt2 = Starter.LeaderCardsParser().get(10);
        game2.getCurrPlayer().getPersonalBoard().addLeaderCard(new LeaderCardExt[]{leaderCardExt1,leaderCardExt2});
        DevelopmentCardExt developmentCardExt1 = Starter.DevCardParser().get(3);
        DevelopmentCardExt developmentCardExt2 = Starter.DevCardParser().get(2);
        DevelopmentCardExt developmentCardExt3 = Starter.DevCardParser().get(6);
        game2.getCurrPlayer().getPersonalBoard().addDevCard(developmentCardExt1,0);
        game2.getCurrPlayer().getPersonalBoard().addDevCard(developmentCardExt2,1);
        game2.getCurrPlayer().getPersonalBoard().addDevCard(developmentCardExt3,2);
        leaderCardExt1.setPlayed(game2.getCurrPlayer());
        leaderCardExt2.setPlayed(game2.getCurrPlayer());
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                game2.getMarketTray().setMarble(i,j,MarbleColor.WHITE);
            }
        }
        MoveTypeMarketExt marketExt4 = new MoveTypeMarketExt(game2.getCurrPlayer().getID());
        marketExt4.setIndexToBuy(3);
        marketExt4.performMove(game2);
        assertEquals(PlayerStatus.NeedToConvert, game2.getCurrPlayer().getStatus());
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

        MoveTypeMarketExt marketExt2 = new MoveTypeMarketExt(game.getCurrPlayer().getID());
        assertNotNull(marketExt2.getClassName());
        assertEquals("MovetypeMarket",marketExt2.getClassName());
    }
}
