package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Starter;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoSoloPlayerTest {

    @Test
    void constructor() throws IOException, ParseException {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCard> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<String> playerArrayList = new ArrayList<>();
        playerArrayList.add("Fabio");

        Game game = new Game(playerArrayList, new Market(Starter.MarblesParser()), new Dashboard(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

        assertNotNull(game.getSoloGame().getGame());
        assertNotNull(game.getSoloGame().getFaithTrack());
        assertEquals(0,game.getSoloGame().getFaithTrack().getFaithPoints());
        assertNotNull(game.getSoloGame().getSoloActionTokens());
        assertNotNull(game.getSoloGame().getCopyOfSoloActionTokensInit());
        assertEquals(7,game.getSoloGame().getSoloActionTokens().size());
        assertEquals(7,game.getSoloGame().getCopyOfSoloActionTokensInit().size());

        for(SoloActionTokens x: game.getSoloGame().getSoloActionTokens()){
            assertTrue(soloActionTokensArrayList.contains(x));
        }
        for (SoloActionTokens x: game.getSoloGame().getCopyOfSoloActionTokensInit()){
            assertTrue(soloActionTokensArrayList.contains(x));
        }
    }

    @Test
    void Setter() throws IOException, ParseException {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCard> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<String> playerArrayList = new ArrayList<>();
        playerArrayList.add("Fabio");

        Game game = new Game(playerArrayList, new Market(Starter.MarblesParser()), new Dashboard(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

        Collections.shuffle(soloActionTokensArrayList);
        game.getSoloGame().setSoloActionTokens(soloActionTokensArrayList);
        assertNotNull(game.getSoloGame().getSoloActionTokens());
        for (SoloActionTokens x: soloActionTokensArrayList){
            assertNotNull(x);
        }
        for (SoloActionTokens x: game.getSoloGame().getSoloActionTokens()){
            assertTrue(soloActionTokensArrayList.contains(x));
        }

    }

    @Test
    void RevealTokenTest() throws IOException, ParseException {
        ArrayList<SoloActionTokens> soloActionTokensArrayList;

        soloActionTokensArrayList = Starter.TokensParser();
        ArrayList<LeaderCard> leaderCards;

        //player.getDepots().addResourceFromProduction(new NumberOfResources(100,100,100,100));
        ArrayList<String> playerArrayList = new ArrayList<>();
        playerArrayList.add("Fabio");

        Game game = new Game(playerArrayList, new Market(Starter.MarblesParser()), new Dashboard(Starter.DevCardParser()), soloActionTokensArrayList, Starter.LeaderCardsParser());

        int dim = game.getSoloGame().getSoloActionTokens().size();
        SoloActionTokens NextRevealedToken = game.getSoloGame().getSoloActionTokens().get(0);
        game.getSoloGame().revealToken();
        ArrayList <SoloActionTokens> temp = game.getSoloGame().getSoloActionTokens();
        assertFalse(temp.contains(NextRevealedToken));                                  //a volte va, a volte no, Boh? TODO
        assertEquals(--dim,game.getSoloGame().getSoloActionTokens().size());

    }
}
