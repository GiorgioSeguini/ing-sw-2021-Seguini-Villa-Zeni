package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    public void UpdateTEST(){
        ArrayList<PlayerExt> players= new ArrayList<>();
        GameExt game;

        PlayerExt player1= new PlayerExt("pippo");
        PlayerExt player2= new PlayerExt("pluto");
        players.add(player1);
        players.add(player2);
        game= new GameExt(players, new MarketExt(Starter.MarblesParser()), new DashboardExt(Starter.DevCardParser()),Starter.TokensParser(),Starter.LeaderCardsParser());

        Controller controller = new Controller(game);
        PlayerExt playerExt = game.getCurrPlayer();
        ArrayList<LeaderCard> arrayList = game.getActivableLeadCard(playerExt);
        MoveChoseLeaderCardsExt moveChoseLeaderCardsExt = new MoveChoseLeaderCardsExt(playerExt.getID());
        controller.update(moveChoseLeaderCardsExt);

        ArrayList<Integer> arrayList1 = new ArrayList<>();
        arrayList1.add(arrayList.get(0).getId());
        arrayList1.add(arrayList.get(1).getId());
        moveChoseLeaderCardsExt.setIndexLeaderCards(arrayList1);
        controller.update(moveChoseLeaderCardsExt);
        assertEquals(game.getCurrIndex(), 1);

    }
}
