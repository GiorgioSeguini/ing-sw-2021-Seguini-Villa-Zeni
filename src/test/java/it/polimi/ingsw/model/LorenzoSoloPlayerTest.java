package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class LorenzoSoloPlayerTest {

    @Test
    void constructor(){
        DepotsAbility depotsAbility = new DepotsAbility(ResourceType.Coins);
        WhiteAbility whiteAbility = new WhiteAbility(ResourceType.Stones);
        LeaderCard[] leaderCards = new LeaderCard[2];
        leaderCards[1] = new LeaderCard(new Requirements(),whiteAbility,3);
        leaderCards[0] = new LeaderCard(new Requirements(), depotsAbility,5);
        Player player = new Player("Fabio",new PersonalBoard(leaderCards),0,new NumberOfResources(0,0,0,0));
        ArrayList<Player> players = new ArrayList();
        players.add(player);

        MarbleColor[] startMarbles  = new MarbleColor[]{MarbleColor.RED, MarbleColor.PURPLE, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.RED, MarbleColor.BLUE};
        Market tray = new Market(startMarbles);

        //Dashboard dashboard = new Dashboard();
        //Game game = new Game(players,tray,dashboard);
        //LorenzoSoloPlayer soloPlayer = new LorenzoSoloPlayer(game, );

        //assertEquals(0, soloPlayer.getFaithTrack().getFaithPoints());
        //assertFalse(soloPlayer.getSoloActionTokens().isEmpty());
        //assertEquals(7,soloPlayer.getSoloActionTokens().size());
        //assertEquals(soloPlayer.getSoloActionTokens(),soloPlayer.getCopyOfSoloActionTokensInit());
    }
}
