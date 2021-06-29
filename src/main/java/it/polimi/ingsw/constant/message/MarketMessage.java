package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.model.Game;

import java.util.ArrayList;

/**
 * MarketMessage class.
 * Implements Message interface.
 * Manage the market messages.
 */
public class MarketMessage implements Message{

    public static final String className = "MarketMessage";
    final ArrayList<MarbleColor> marbles;

    /**
     * Instantiates a new Market message.
     *
     * @param marbles of type ArrayList<MarbleColor>: the marbles.
     */
    public MarketMessage(ArrayList<MarbleColor> marbles) {
        this.marbles = new ArrayList<>();
        this.marbles.addAll(marbles);
    }

    /**
     * Handle the market messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.getMarketTray().setMarbles(marbles);
    }

    /**
     *
     * @return of type String: the class name, useful for json serialization.
     */
    @Override
    public String getName() {
        return className;
    }
}
