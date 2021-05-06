package it.polimi.ingsw.server.parse;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void test() throws FileNotFoundException {
        ArrayList<Player> due = new ArrayList<>();
        due.add(new Player("Pippo"));
        due.add(new Player("Piero"));


        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 1));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 2));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 3));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 4));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 5));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 6));
        leaderCards.add(new LeaderCard(new Requirements(), new WhiteAbility(ResourceType.Coins), 7));

        Market market = new Market(Starter.MarblesParser());
        Dashboard dashboard = new Dashboard(new ArrayList<DevelopmentCard>());

        Game game = new Game(due, market, dashboard, new ArrayList<SoloActionTokens>(), leaderCards);

        System.out.println(Starter.toJson(game));

    }
}
