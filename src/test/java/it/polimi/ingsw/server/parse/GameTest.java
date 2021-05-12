package it.polimi.ingsw.server.parse;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.CLI;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.GameMessage;
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

        System.out.println(Starter.toJson(game, Game.class));

        Client client= new Client("127.0.0.1", 12345);

        String model = Starter.toJson(game, Game.class);
        int myID = due.get(0).getID();
        Type type = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
        String leaderCardsString = Starter.toJson(game.getActivableLeadCard(due.get(0)), type);

        Message msg = new InitialMessage(model, myID, leaderCardsString);

        String packet = Starter.toJson(msg, Message.class);
        System.out.println(packet);
        Message recived = StarterClient.fromJson(packet, Message.class);

        recived.handleMessage(client);

        assertNotNull(client.getSimpleGame());

    }
}
