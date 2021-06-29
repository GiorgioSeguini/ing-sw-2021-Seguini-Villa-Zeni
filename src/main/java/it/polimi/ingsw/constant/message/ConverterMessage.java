package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Converter;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;

/**
 * ConverterMessage class.
 * Implements Message interface.
 * Manage the converter's messages.
 */
public class ConverterMessage implements Message{

    public static final String className = "ConverterMessage";
    private final Converter converter;

    /**
     * Instantiates a new Converter message.
     *
     * @param converter of type Converter: the converter.
     */
    public ConverterMessage(Converter converter) {
        this.converter = converter;
    }

    /**
     * Handle the converter's message.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
        Game game = client.getSimpleGame();
        Player owner = game.getPlayerFromID(converter.getOwnerId());
        owner.setConverter(converter);
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
