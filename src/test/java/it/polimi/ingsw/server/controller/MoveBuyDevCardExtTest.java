package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.*;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveBuyDevCardExtTest {
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
    public void CanPerform() throws UnableToFillException, FileNotFoundException {
        game.setStatus(GameStatus.Running);
        game.setIndex(game.getPlayerIndex(players.get(0)));
        DevelopmentCardExt cardtobuy= game.getDashboard().getTopDevCard(ColorDevCard.YELLOW,Level.ONE);
        MoveBuyDevCardExt move= new MoveBuyDevCardExt(players.get(0).getID());
        move.setIndexCardToBuy(cardtobuy.getId());
        move.setPos(0);

        assertFalse(move.canPerformExt(game));
        game.getPlayerFromID(players.get(0).getID()).setStatus(PlayerStatus.Active);
        game.getPlayerFromID(players.get(1).getID()).setStatus(PlayerStatus.Waiting);
        assertFalse(move.canPerformExt(game));
        game.getPlayerFromID(players.get(0).getID()).getDepots().addResourcesFromMarket(cardtobuy.getCost());
        assertTrue(move.canPerformExt(game));
        game.getDashboard().buyDevCard(ColorDevCard.YELLOW,Level.ONE);
        assertFalse(move.canPerformExt(game));

        for (DevelopmentCardExt card: Starter.DevCardParser()){
            if(!card.equals(game.getDashboard().getTopDevCard(card.getColor(),card.getLevel()))){
                cardtobuy=card;
                break;
            }
        }
        move.setIndexCardToBuy(cardtobuy.getId());
        assertFalse(move.canPerformExt(game));
    }

    @Test
    public void PerformMove() throws UnableToFillException {
        game.setStatus(GameStatus.Running);
        game.setIndex(game.getPlayerIndex(players.get(1)));
        players.get(1).setStatus(PlayerStatus.Active);
        players.get(0).setStatus(PlayerStatus.Waiting);

        MoveBuyDevCardExt move= new MoveBuyDevCardExt(players.get(1).getID());
        DevelopmentCardExt card= game.getDashboard().getTopDevCard(ColorDevCard.BLUE, Level.ONE);
        players.get(1).getDepots().addResourcesFromMarket(card.getCost());

        move.setIndexCardToBuy(card.getId());
        move.setPos(0);

        move.performMove(game);

        assertEquals(new NumberOfResources(), game.getPlayerFromID(players.get(1).getID()).getDepots().getResources());
        assertEquals(1, game.getPlayerFromID(players.get(1).getID()).getPersonalBoard().getOwnedDevCards().size());
        assertEquals(card,game.getPlayerFromID(players.get(1).getID()).getPersonalBoard().getOwnedDevCards().get(0));

        card= game.getDashboard().getTopDevCard(ColorDevCard.BLUE, Level.ONE);
        players.get(1).getDepots().addResourcesFromMarket(card.getCost());
        move.setIndexCardToBuy(card.getId());
        move.setPos(0);

        move.performMove(game);

        assertEquals(ErrorMessage.NoSpaceError, game.getPlayerFromID(players.get(1).getID()).getErrorMessage());
    }

}