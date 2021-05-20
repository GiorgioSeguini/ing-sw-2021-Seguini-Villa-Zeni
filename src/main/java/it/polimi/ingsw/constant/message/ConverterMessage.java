package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Converter;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;

public class ConverterMessage implements Message{
    public static final String className = "ConverterMessage";

    private final Converter converter;

    public ConverterMessage(Converter converter) {
        this.converter = converter;
    }

    @Override
    public void handleMessage(Client client) {
        Game game = client.getSimpleGame();
        Player owner = game.getPlayerFromID(converter.getOwnerId());
        owner.setConverter(converter);
    }

    @Override
    public String getName() {
        return className;
    }
}
