package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.enumeration.ColorDevCard;
import it.polimi.ingsw.server.model.enumeration.GameStatus;
import it.polimi.ingsw.server.model.enumeration.Level;
import it.polimi.ingsw.server.model.enumeration.MarbleColor;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        Market marketTray = new Market(startMarbles);

        ArrayList<DevelopmentCard> devcards = new ArrayList<>();
        try {
            devcards = Starter.DevCardParser();
        } catch (IOException | ParseException e) {
            fail();
        }
        Dashboard dashboard = new Dashboard(devcards);

        ArrayList<SoloActionTokens> soloActionTokens = new ArrayList<>();

        ArrayList<LeaderCard>leaderCards=new ArrayList<>();
        try {
            leaderCards=Starter.LeaderCardsParser();
        } catch (IOException | ParseException e) {
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
            //assertTrue(x.getDepots().getResources().size() == 0);
            //System.out.println(game.getInitialResources(x));
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
}
