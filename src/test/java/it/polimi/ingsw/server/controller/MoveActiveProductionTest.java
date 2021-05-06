package it.polimi.ingsw.server.controller;
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
        Player player = new Player("Fabio");
        ProductionPower productionPowerbase = new ProductionPower();
        ProductionPower productionPower = new ProductionPower(new NumberOfResources(0,0,0,1), new NumberOfResources(0,2,0,0));
        ArrayList<ProductionPower> toActive = new ArrayList<>();
        toActive.add(productionPowerbase);
        toActive.add(productionPower);
        MoveActiveProduction moveActiveProduction = new MoveActiveProduction(player,toActive);
        assertNotNull(moveActiveProduction.player);
        for(ProductionPower x : moveActiveProduction.toActive) {
            assertNotNull(x);
        }
        assertNotNull(moveActiveProduction.allowedStatus);
    }

    @Test
    public void CanPerformTest() throws NoSpaceException {
        Player player1 = new Player("Fabio");
        Player player2 = new Player("Pippo");
        ArrayList<Player> players = new ArrayList<>(3);
        players.add(player1);
        players.add(player2);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        try {
            startMarbles = Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Market marketTray = new Market(startMarbles);

        ArrayList<DevelopmentCard> devcards = new ArrayList<>();
        try {
            devcards = Starter.DevCardParser();
        } catch (IOException e) {
            fail();
        }
        Dashboard dashboard = new Dashboard(devcards);

        ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<>();

        ArrayList<LeaderCard>leaderCards=new ArrayList<>();
        try {
            leaderCards=Starter.LeaderCardsParser();
        } catch (IOException e) {
            fail();
        }

        Game game = new Game(players, marketTray, dashboard, soloActionTokens, leaderCards);
        for(Player x : players){
            LeaderCard[] leaderCardsarray= new LeaderCard[] {leaderCards.get(0),leaderCards.get(1)};
            leaderCards.remove(1);
            leaderCards.remove(0);
            x.getPersonalBoard().addLeaderCard(leaderCardsarray);
        }
        for(Player x : game.getPlayers()){
            assertTrue(x.getPersonalBoard().isReady());
        }
        game.getPlayer(1).getDepots().addResourceFromProduction(new NumberOfResources(1,0,0,0));
        for(Player x : game.getPlayers()){
            assertTrue(x.getDepots().getResources().size() == game.getInitialResources(x));
        }
        game.updateStatus();
        ColorDevCard colorDevCard = ColorDevCard.GREEN;
        Level level = Level.ONE;
        game.getPlayer(0).getDepots().addResourceFromProduction(new NumberOfResources(10,10,10,10));
        game.getPlayer(0).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard, level),0);
        ArrayList<ProductionPower> productionOwned = game.getPlayer(0).getPersonalBoard().getProduction();
        MoveActiveProduction moveActiveProduction = new MoveActiveProduction(game.getCurrPlayer(), productionOwned);
        assertTrue(moveActiveProduction.canPerform(game));

        productionOwned = game.getPlayer(1).getPersonalBoard().getProduction();
        MoveActiveProduction moveActiveProduction1 = new MoveActiveProduction(game.getPlayer(1),productionOwned);
        assertFalse(moveActiveProduction1.canPerform(game));

        productionOwned = new ArrayList<ProductionPower>();
        productionOwned.add(new ProductionPower());
        MoveActiveProduction moveActiveProduction2 = new MoveActiveProduction(game.getCurrPlayer(), productionOwned);
        assertFalse(moveActiveProduction2.canPerform(game));

    }

    @Test
    public void PerformMoveTest() throws NoSpaceException, OutOfResourcesException {
        Player player1 = new Player("Fabio");
        Player player2 = new Player("Pippo");
        ArrayList<Player> players = new ArrayList<>(3);
        players.add(player1);
        players.add(player2);

        ArrayList<MarbleColor> startMarbles = new ArrayList<>();
        try {
            startMarbles = Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Market marketTray = new Market(startMarbles);

        ArrayList<DevelopmentCard> devcards = new ArrayList<>();
        try {
            devcards = Starter.DevCardParser();
        } catch (IOException e) {
            fail();
        }
        Dashboard dashboard = new Dashboard(devcards);

        ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<>();

        ArrayList<LeaderCard>leaderCards=new ArrayList<>();
        try {
            leaderCards=Starter.LeaderCardsParser();
        } catch (IOException e) {
            fail();
        }

        Game game = new Game(players, marketTray, dashboard, soloActionTokens, leaderCards);
        for(Player x : players){
            LeaderCard[] leaderCardsarray= new LeaderCard[] {leaderCards.get(0),leaderCards.get(1)};
            leaderCards.remove(1);
            leaderCards.remove(0);
            x.getPersonalBoard().addLeaderCard(leaderCardsarray);
        }
        for(Player x : game.getPlayers()){
            assertTrue(x.getPersonalBoard().isReady());
        }
        game.getPlayer(1).getDepots().addResourceFromProduction(new NumberOfResources(1,0,0,0));
        game.updateStatus();
        ColorDevCard colorDevCard = ColorDevCard.GREEN;
        Level level = Level.ONE;
        game.getPlayer(0).getDepots().addResourceFromProduction(new NumberOfResources(10,10,10,10));
        game.getPlayer(0).getPersonalBoard().addDevCard(dashboard.buyDevCard(colorDevCard, level),0);
        ArrayList<ProductionPower> productionOwned = game.getCurrPlayer().getPersonalBoard().getProduction();

        MoveActiveProduction moveActiveProduction = new MoveActiveProduction(player1,productionOwned);
        moveActiveProduction.performMove(game);

        //need to chose
        assertNotNull(game.getCurrPlayer().getToActive());
        assertEquals(PlayerStatus.NeedToChoseRes,game.getCurrPlayer().getStatus());
        assertFalse(game.getCurrPlayer().getToActive().easyActive());

        //easyactive
        moveActiveProduction.toActive.remove(0);

        moveActiveProduction.performMove(game);
        assertTrue(game.getCurrPlayer().getToActive().easyActive());

        //fail performmove
        Player player3 = new Player("Fabio");
        Player player4 = new Player("Pippo");
        ArrayList<Player> players2 = new ArrayList<>(3);
        players2.add(player3);
        players2.add(player4);

        ArrayList<MarbleColor> startMarbles2 = new ArrayList<>();
        try {
            startMarbles2 = Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Market marketTray2 = new Market(startMarbles2);

        ArrayList<DevelopmentCard> devcards2 = new ArrayList<>();
        try {
            devcards2 = Starter.DevCardParser();
        } catch (IOException e) {
            fail();
        }
        Dashboard dashboard2 = new Dashboard(devcards2);

        ArrayList<SoloActionTokens> soloActionTokens2 = new ArrayList<>();

        ArrayList<LeaderCard>leaderCards2=new ArrayList<>();
        try {
            leaderCards2=Starter.LeaderCardsParser();
        } catch (IOException e) {
            fail();
        }

        Game game2 = new Game(players2, marketTray2, dashboard2, soloActionTokens2, leaderCards2);
        for(Player x : players2){
            LeaderCard[] leaderCardsarray= new LeaderCard[] {leaderCards2.get(0),leaderCards2.get(1)};
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
        ArrayList<ProductionPower> productionOwned2 = game2.getCurrPlayer().getPersonalBoard().getProduction();


        MoveActiveProduction moveActiveProduction2 = new MoveActiveProduction(player3,productionOwned2);
        moveActiveProduction2.toActive.remove(0);
        moveActiveProduction2.performMove(game2);
        assertFalse(game2.getCurrPlayer().isActivable());
        assertNull(game2.getCurrPlayer().getToActive());
        assertEquals(ErrorMessage.OutOfResourcesError,game2.getCurrPlayer().getErrorMessage());



    }
}
