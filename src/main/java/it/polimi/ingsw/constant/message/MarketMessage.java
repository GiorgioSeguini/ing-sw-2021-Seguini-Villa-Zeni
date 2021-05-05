package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.constant.enumeration.MarbleColor;

import java.util.ArrayList;

public class MarketMessage implements Message{

    ArrayList<MarbleColor> marbles;

    public MarketMessage(ArrayList<MarbleColor> marbles) {
        this.marbles = new ArrayList<>();
        this.marbles.addAll(marbles);
    }

    @Override
    public void handleMessage(Game simpleGame){
        simpleGame.getMarketTray().setMarbles(marbles);
    }
}
