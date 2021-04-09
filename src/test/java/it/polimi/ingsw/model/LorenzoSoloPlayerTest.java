package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoSoloPlayerTest {

    @Test
    void constructor(){
        Player player = new Player("Fabio",0,new NumberOfResources(0,0,0,0));
        ArrayList<Player> players = new ArrayList();
        players.add(player);
        Market tray = new Market();
        Dashboard dashboard = new Dashboard();
        Game game = new Game(players,tray,dashboard);
        LorenzoSoloPlayer soloPlayer = new LorenzoSoloPlayer(game);

        assertEquals(0, soloPlayer.getFaithTrack().getFaithPoints());
        //assertFalse(soloPlayer.getSoloActionTokens().isEmpty());
        //assertEquals(7,soloPlayer.getSoloActionTokens().size());
        assertEquals(soloPlayer.getSoloActionTokens(),soloPlayer.getCopyOfSoloActionTokensInit());
    }
}
