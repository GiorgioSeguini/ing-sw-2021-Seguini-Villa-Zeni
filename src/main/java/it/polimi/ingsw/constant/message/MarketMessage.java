package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.MarbleColor;

import java.util.ArrayList;

public class MarketMessage implements Message{

    public static final String className = "MarketMessage";
    ArrayList<MarbleColor> marbles;

    public MarketMessage(ArrayList<MarbleColor> marbles) {
        this.marbles = new ArrayList<>();
        this.marbles.addAll(marbles);
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.getMarketTray().setMarbles(marbles);
    }

    @Override
    public String getName() {
        return className;
    }
}
