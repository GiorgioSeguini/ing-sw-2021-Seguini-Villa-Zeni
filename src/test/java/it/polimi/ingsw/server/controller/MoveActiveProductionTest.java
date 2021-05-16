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

public class MoveActiveProductionTest {

    @Test
    public void ConstructorTest(){
        PlayerExt player = new PlayerExt("Fabio");
        ProductionPowerExt productionPowerbase = new ProductionPowerExt();
        ProductionPowerExt productionPower = new ProductionPowerExt(new NumberOfResources(0,0,0,1), new NumberOfResources(0,2,0,0));
        ArrayList<ProductionPowerExt> toActive = new ArrayList<>();
        toActive.add(productionPowerbase);
        toActive.add(productionPower);
        /*MoveActiveProductionExt moveActiveProduction = new MoveActiveProductionExt(player.getID(),toActive);
        assertNotNull(moveActiveProduction.getIdPlayer());
        for(ProductionPower x : moveActiveProduction.getToActive()) {
            assertNotNull(x);
        }*/
    }

    @Test
    public void CanPerformTest() throws NoSpaceException {
        PlayerExt player1 = new PlayerExt("Fabio");
        PlayerExt player2 = new PlayerExt("Pippo");
        ArrayList<PlayerExt> players = new ArrayList<>(3);
        players.add(player1);
        players.add(player2);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        try {
            startMarbles = Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MarketExt marketTray = new MarketExt(startMarbles);

        ArrayList<DevelopmentCardExt> devcards = new ArrayList<>();
        try {
            devcards = Starter.DevCardParser();
        } catch (IOException e) {
            fail();
        }
        DashboardExt dashboard = new DashboardExt(devcards);

        ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<>();

        ArrayList<LeaderCardExt>leaderCards=new ArrayList<>();
        try {
            leaderCards=Starter.LeaderCardsParser();
        } catch (IOException e) {
            fail();
        }

        GameExt game = new GameExt(players, marketTray, dashboard, soloActionTokens, leaderCards);
        for(PlayerExt x : players){
            LeaderCardExt[] leaderCardsarray= new LeaderCardExt[] {leaderCards.get(0),leaderCards.get(1)};
            leaderCards.remove(1);
            leaderCards.remove(0);
            x.getPersonalBoard().addLeaderCard(leaderCardsarray);
        }
        for(Player x : game.getPlayers()){
            assertTrue(x.getPersonalBoard().isReady());
        }
        ((PlayerExt)game.getPlayers().get(1)).getDepots().addResourceFromProduction(new NumberOfResources(1,0,0,0));
        for(Player x : game.getPlayers()){
            assertTrue(x.getDepots().getResources().size() == game.getInitialResources(x));
        }
        game.updateStatus();
        ColorDevCard colorDevCard = ColorDevCard.GREEN;
        Level level = Level.ONE;
        ((PlayerExt)game.getPlayers().get(0)).getDepots().addResourceFromProduction(new NumberOfResources(10,10,10,10));
        ((PlayerExt)game.getPlayers().get(0)).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard, level),0);
        /*ArrayList<ProductionPowerExt> productionOwned = ((PlayerExt)game.getPlayers().get(0)).getPersonalBoard().getProduction();
        MoveActiveProductionExt moveActiveProduction = new MoveActiveProductionExt(game.getCurrPlayer().getID(), productionOwned);
        assertTrue(moveActiveProduction.canPerform(game));

        productionOwned =((PlayerExt)game.getPlayers().get(1)).getPersonalBoard().getProduction();
        MoveActiveProductionExt moveActiveProduction1 = new MoveActiveProductionExt(((PlayerExt)game.getPlayers().get(1)).getID(),productionOwned);
        assertFalse(moveActiveProduction1.canPerform(game));

        productionOwned = new ArrayList<ProductionPowerExt>();
        productionOwned.add(new ProductionPowerExt());
        MoveActiveProductionExt moveActiveProduction2 = new MoveActiveProductionExt(game.getCurrPlayer().getID(), productionOwned);
        assertFalse(moveActiveProduction2.canPerform(game));*/

    }

    @Test
    public void PerformMoveTest() throws NoSpaceException, OutOfResourcesException {
        PlayerExt player1 = new PlayerExt("Fabio");
        PlayerExt player2 = new PlayerExt("Pippo");
        ArrayList<PlayerExt> players = new ArrayList<>(3);
        players.add(player1);
        players.add(player2);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        try {
            startMarbles = Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MarketExt marketTray = new MarketExt(startMarbles);

        ArrayList<DevelopmentCardExt> devcards = new ArrayList<>();
        try {
            devcards = Starter.DevCardParser();
        } catch (IOException e) {
            fail();
        }
        DashboardExt dashboard = new DashboardExt(devcards);

        ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<>();

        ArrayList<LeaderCardExt>leaderCards=new ArrayList<>();
        try {
            leaderCards=Starter.LeaderCardsParser();
        } catch (IOException e) {
            fail();
        }

        GameExt game = new GameExt(players, marketTray, dashboard, soloActionTokens, leaderCards);
        for(PlayerExt x : players){
            LeaderCardExt[] leaderCardsarray= new LeaderCardExt[] {leaderCards.get(0),leaderCards.get(1)};
            leaderCards.remove(1);
            leaderCards.remove(0);
            x.getPersonalBoard().addLeaderCard(leaderCardsarray);
        }
        for(Player x : game.getPlayers()){
            assertTrue(x.getPersonalBoard().isReady());
        }
        /*
        ((PlayerExt)game.getPlayers().get(1)).getDepots().addResourceFromProduction(new NumberOfResources(1,0,0,0));
        game.updateStatus();
        ColorDevCard colorDevCard = ColorDevCard.GREEN;
        Level level = Level.ONE;
        ((PlayerExt)game.getPlayers().get(0)).getDepots().addResourceFromProduction(new NumberOfResources(10,10,10,10));
        ((PlayerExt)game.getPlayers().get(0)).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard, level),0);
        ArrayList<ProductionPowerExt> productionOwned = game.getCurrPlayer().getPersonalBoard().getProduction();

        MoveActiveProductionExt moveActiveProduction = new MoveActiveProductionExt(player1.getID(),productionOwned);
        moveActiveProduction.performMove(game);

        //need to chose
        assertNotNull(game.getCurrPlayerExt().getToActive());
        assertEquals(PlayerExtStatus.NeedToChoseRes,game.getCurrPlayerExt().getStatus());
        assertFalse(game.getCurrPlayerExt().getToActive().easyActive());

        //easyactive
        moveActiveProduction.toActive.remove(0);

        moveActiveProduction.performMove(game);
        assertTrue(game.getCurrPlayerExt().getToActive().easyActive());

        //fail performmove
        PlayerExt player3 = new PlayerExt("Fabio");
        PlayerExt player4 = new PlayerExt("Pippo");
        ArrayList<PlayerExt> players2 = new ArrayList<>(3);
        players2.add(player3);
        players2.add(player4);

        ArrayList<MarbleColor> startMarbles2 = new ArrayList<>();
        try {
            startMarbles2 = Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MarketExt marketTray2 = new MarketExt(startMarbles2);

        ArrayList<DevelopmentCardExt> devcards2 = new ArrayList<>();
        try {
            devcards2 = Starter.DevCardParser();
        } catch (IOException e) {
            fail();
        }
        DashboardExt dashboard2 = new DashboardExt(devcards2);

        ArrayList<SoloActionTokens> soloActionTokens2 = new ArrayList<>();

        ArrayList<LeaderCardExt>leaderCards2=new ArrayList<>();
        try {
            leaderCards2=Starter.LeaderCardsParser();
        } catch (IOException e) {
            fail();
        }

        GameExt game2 = new GameExt(players2, marketTray2, dashboard2, soloActionTokens2, leaderCards2);
        for(PlayerExt x : players2){
            LeaderCardExt[] leaderCardsarray= new LeaderCardExt[] {leaderCards2.get(0),leaderCards2.get(1)};
            leaderCards2.remove(1);
            leaderCards2.remove(0);
            x.getPersonalBoard().addLeaderCard(leaderCardsarray);
        }
        for(PlayerExt x : game2.getPlayerExts()){
            assertTrue(x.getPersonalBoard().isReady());
        }
        game2.getPlayerExt(1).getDepots().addResourceFromProduction(new NumberOfResources(1,0,0,0));
        game2.updateStatus();
        ColorDevCard colorDevCard2 = ColorDevCard.GREEN;
        Level level2 = Level.ONE;
        game2.getPlayerExt(0).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard2, level2),0);
        ArrayList<ProductionPowerExt> productionOwned2 = game2.getCurrPlayerExt().getPersonalBoard().getProduction();


        MoveActiveProductionExt moveActiveProduction2 = new MoveActiveProductionExt(player3.getID(),productionOwned2);
        moveActiveProduction2.toActive.remove(0);
        moveActiveProduction2.performMove(game2);
        assertFalse(game2.getCurrPlayerExt().isActivable());
        assertNull(game2.getCurrPlayerExt().getToActive());
        assertEquals(ErrorMessage.OutOfResourcesError,game2.getCurrPlayerExt().getErrorMessage());

*/

    }
}
