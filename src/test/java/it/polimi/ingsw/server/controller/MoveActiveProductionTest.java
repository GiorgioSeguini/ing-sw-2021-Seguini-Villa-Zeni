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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
public class MoveActiveProductionTest {

    @Test
    public void ConstructorTest(){
        PlayerExt player = new PlayerExt("Fabio");
        ProductionPowerExt productionPowerbase = new ProductionPowerExt();
        ProductionPowerExt productionPower = new ProductionPowerExt(new NumberOfResources(0,0,0,1), new NumberOfResources(0,2,0,0));
        ArrayList<ProductionPowerExt> toActive = new ArrayList<>();
        toActive.add(productionPowerbase);
        toActive.add(productionPower);
        MoveActiveProductionExt moveActiveProduction = new MoveActiveProductionExt(player.getID(),toActive);
        for(ProductionPower x : moveActiveProduction.getToActive()) {
            assertNotNull(x);
        }
    }

    @Test
    public void CanPerformTest() throws NoSpaceException {
        PlayerExt player1 = new PlayerExt("Fabio");
        PlayerExt player2 = new PlayerExt("Pippo");
        ArrayList<PlayerExt> players = new ArrayList<>(3);
        players.add(player1);
        players.add(player2);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        startMarbles = Starter.MarblesParser();
        MarketExt marketTray = new MarketExt(startMarbles);

        ArrayList<DevelopmentCardExt> devcards = new ArrayList<>();
        devcards = Starter.DevCardParser();
        DashboardExt dashboard = new DashboardExt(devcards);

        ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<>();

        ArrayList<LeaderCardExt>leaderCards=new ArrayList<>();
        leaderCards=Starter.LeaderCardsParser();

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
            assertTrue(x.getDepots().getResources().size() == game.getInitialResources(x.getID()));
        }
        game.updateStatus();
        ColorDevCard colorDevCard = ColorDevCard.GREEN;
        Level level = Level.ONE;
        ((PlayerExt)game.getPlayers().get(0)).getDepots().addResourceFromProduction(new NumberOfResources(10,10,10,10));
        ((PlayerExt)game.getPlayers().get(0)).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard, level),0);
        ArrayList<ProductionPowerExt> productionOwned = new ArrayList<>();
        for(ProductionPower p : ((PlayerExt)game.getPlayers().get(0)).getPersonalBoard().getProduction()){
            productionOwned.add((ProductionPowerExt) p);
        }
        MoveActiveProductionExt moveActiveProduction = new MoveActiveProductionExt(game.getCurrPlayer().getID(), productionOwned);
        assertTrue(moveActiveProduction.canPerformExt(game));

        productionOwned = new ArrayList<>();
        for(ProductionPower p : ((PlayerExt)game.getPlayers().get(1)).getPersonalBoard().getProduction()){
            productionOwned.add((ProductionPowerExt) p);
        }
        MoveActiveProductionExt moveActiveProduction1 = new MoveActiveProductionExt(((PlayerExt)game.getPlayers().get(1)).getID(),productionOwned);
        assertFalse(moveActiveProduction1.canPerformExt(game));

        productionOwned = new ArrayList<ProductionPowerExt>();
        productionOwned.add(new ProductionPowerExt());
        MoveActiveProductionExt moveActiveProduction2 = new MoveActiveProductionExt(game.getCurrPlayer().getID(), productionOwned);
        assertFalse(moveActiveProduction2.canPerformExt(game));

    }

    @Test
    public void PerformMoveTest() throws NoSpaceException {
        PlayerExt player1 = new PlayerExt("Fabio");
        PlayerExt player2 = new PlayerExt("Pippo");
        ArrayList<PlayerExt> players = new ArrayList<>(3);
        players.add(player1);
        players.add(player2);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        startMarbles = Starter.MarblesParser();
        MarketExt marketTray = new MarketExt(startMarbles);

        ArrayList<DevelopmentCardExt> devcards = new ArrayList<>();
        devcards = Starter.DevCardParser();
        DashboardExt dashboard = new DashboardExt(devcards);

        ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<>();

        ArrayList<LeaderCardExt>leaderCards=new ArrayList<>();
        leaderCards=Starter.LeaderCardsParser();

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
        game.updateStatus();
        ColorDevCard colorDevCard = ColorDevCard.GREEN;
        Level level = Level.ONE;
        ((PlayerExt)game.getPlayers().get(0)).getDepots().addResourceFromProduction(new NumberOfResources(10,10,10,10));
        ((PlayerExt)game.getPlayers().get(0)).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard, level),0);
        ArrayList<ProductionPowerExt> productionOwned = new ArrayList<>();
        for(ProductionPower p : game.getCurrPlayer().getPersonalBoard().getProduction()){
            productionOwned.add((ProductionPowerExt) p);
        }

        MoveActiveProductionExt moveActiveProduction = new MoveActiveProductionExt(player1.getID(),productionOwned);
        moveActiveProduction.performMove(game);

        //need to chose
        assertNotNull(game.getCurrPlayer().getToActive());
        assertEquals(PlayerStatus.NeedToChoseRes,game.getCurrPlayer().getStatus());
        assertFalse(game.getCurrPlayer().getToActive().easyActive());

        //easyactive
        moveActiveProduction.getToActive().remove(0);

        moveActiveProduction.performMove(game);
        assertTrue(game.getCurrPlayer().getToActive().easyActive());

        //fail performmove
        PlayerExt player3 = new PlayerExt("Fabio");
        PlayerExt player4 = new PlayerExt("Pippo");
        ArrayList<PlayerExt> players2 = new ArrayList<>(3);
        players2.add(player3);
        players2.add(player4);

        ArrayList<MarbleColor> startMarbles2 = new ArrayList<>();
        startMarbles2 = Starter.MarblesParser();
        MarketExt marketTray2 = new MarketExt(startMarbles2);

        ArrayList<DevelopmentCardExt> devcards2 = new ArrayList<>();
        devcards2 = Starter.DevCardParser();
        DashboardExt dashboard2 = new DashboardExt(devcards2);

        ArrayList<SoloActionTokens> soloActionTokens2 = new ArrayList<>();

        ArrayList<LeaderCardExt>leaderCards2=new ArrayList<>();
        leaderCards2=Starter.LeaderCardsParser();

        GameExt game2 = new GameExt(players2, marketTray2, dashboard2, soloActionTokens2, leaderCards2);
        for(PlayerExt x : players2){
            LeaderCardExt[] leaderCardsarray= new LeaderCardExt[] {leaderCards2.get(0),leaderCards2.get(1)};
            leaderCards2.remove(1);
            leaderCards2.remove(0);
            x.getPersonalBoard().addLeaderCard(leaderCardsarray);
        }
        for(Player x : game2.getPlayers()){
            assertTrue(x.getPersonalBoard().isReady());
        }
        game2.getPlayer(1).getDepots().addResourceFromProduction(new NumberOfResources(1,0,0,0));
        game2.updateStatus();
        ColorDevCard colorDevCard2 = ColorDevCard.GREEN;
        Level level2 = Level.ONE;
        game2.getPlayer(0).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard2, level2),0);
        ArrayList<ProductionPowerExt> productionOwned2 = new ArrayList<>();
        for(ProductionPower p : game2.getCurrPlayer().getPersonalBoard().getProduction()){
            productionOwned2.add((ProductionPowerExt) p);
        }
        MoveActiveProductionExt moveActiveProduction2 = new MoveActiveProductionExt(player3.getID(),productionOwned2);
        moveActiveProduction2.getToActive().remove(0);
        moveActiveProduction2.performMove(game2);
        assertFalse(game2.getCurrPlayer().isActivable());
        assertNull(game2.getCurrPlayer().getToActive());
        assertEquals(ErrorMessage.OutOfResourcesError,game2.getCurrPlayer().getErrorMessage());



    }
}
