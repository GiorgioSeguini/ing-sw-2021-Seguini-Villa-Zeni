package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.server.view.View;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

public class MoveChoseLeaderCardExtTest {
    private static ArrayList<PlayerExt> players= new ArrayList<>();
    private static GameExt game;
    private static Controller controller;

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
        controller= new Controller(game);
    }

    @Test
    public void canPerform() {
        MoveChoseLeaderCardsExt move= new MoveChoseLeaderCardsExt(players.get(0).getID());
        game.setIndex(players.get(0).getID());
        ArrayList<LeaderCard> cards= game.getActivableLeadCard(players.get(0));
        ArrayList<Integer> ids= new ArrayList<>();
        ids.add(cards.get(0).getId());
        cards.remove(0);
        ids.add(cards.get(0).getId());
        cards.remove(0);
        move.setIndexLeaderCards(ids);

        assertTrue(move.canPerformExt(game));

        game.setStatus(GameStatus.Running);
        assertFalse(move.canPerformExt(game));
        game.setStatus(GameStatus.Initial);
        MoveChoseLeaderCardsExt move2= new MoveChoseLeaderCardsExt(players.get(1).getID());
        ids.clear();
        ids.add(cards.get(0).getId());
        cards.remove(0);
        ids.add(cards.get(0).getId());
        cards.remove(0);
        move2.setIndexLeaderCards(ids);
        assertFalse(move2.canPerformExt(game));
        game.setIndex(players.get(1).getID());
        assertFalse(move2.canPerformExt(game));
    }

    @Test
    public void performMove() throws FileNotFoundException {
        MoveChoseLeaderCardsExt move= new MoveChoseLeaderCardsExt(players.get(0).getID());
        game.setIndex(players.get(0).getID());
        ArrayList<LeaderCardExt> cards= Starter.LeaderCardsParser();
        ArrayList<Integer> ids= new ArrayList<>();
        ids.add(cards.get(0).getId());
        cards.remove(0);
        ids.add(cards.get(0).getId());
        cards.remove(0);
        move.setIndexLeaderCards(ids);

        move.performMove(game);
        assertEquals(game.findLeaderCard(ids.get(0)),players.get(0).getPersonalBoard().getLeaderCards().get(0));
        ids.add(cards.get(0).getId());

        move.performMove(game);

        assertEquals(ErrorMessage.BadChoice,game.getPlayerFromID(players.get(0).getID()).getErrorMessage());

    }

    @Test
    public void getClassname(){
        MoveChoseLeaderCardsExt move= new MoveChoseLeaderCardsExt(players.get(0).getID());
        assertEquals("MoveChoseLeaderCards", move.getClassName());
    }
}
