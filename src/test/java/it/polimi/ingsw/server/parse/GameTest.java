package it.polimi.ingsw.server.parse;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.InitialMessage;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void test() {
        ArrayList<PlayerExt> due = new ArrayList<>();
        due.add(new PlayerExt("Pippo"));
        due.add(new PlayerExt("Piero"));


        ArrayList<LeaderCardExt> leaderCards = new ArrayList<>();
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 0));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 1));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 2));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 3));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 4));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 5));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 6));
        leaderCards.add(new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 7));

        MarketExt market = new MarketExt(Starter.MarblesParser());
        DashboardExt dashboard = new DashboardExt(new ArrayList<DevelopmentCardExt>());

        GameExt game = new GameExt(due, market, dashboard, new ArrayList<SoloActionTokens>(), leaderCards);

        System.out.println(Starter.toJson(game, GameExt.class));

        Client client= new Client("127.0.0.1", 12345, 1);

        String model = Starter.toJson(game, GameExt.class);
        int myID = due.get(0).getID();
        Type type = new TypeToken<ArrayList<LeaderCardExt>>(){}.getType();
        String leaderCardsString = Starter.toJson(game.getActivableLeadCard(due.get(0)), type);

        Message msg = new InitialMessage(game, myID, game.getActivableLeadCard(due.get(0)), "room");

        String packet = Starter.toJson(msg, Message.class);
        System.out.println(packet);
        Message recived = StarterClient.fromJson(packet, Message.class);

        recived.handleMessage(client);

        assertNotNull(client.getSimpleGame());

    }
}
