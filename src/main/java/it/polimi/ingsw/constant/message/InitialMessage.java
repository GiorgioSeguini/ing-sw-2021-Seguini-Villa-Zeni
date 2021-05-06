package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.parser.StarterClient;

public class InitialMessage implements Message{

    public static final String className = "InitialMessage";

    private final String model;

    public InitialMessage(String model) {
        this.model = model;
    }

    @Override
    public void handleMessage(Game simpleGame) {
        Game game = StarterClient.fromJsonGame(model);
        simpleGame = game;  //TODO cosi non si può fare
    }

    @Override
    public String getName() {
        return className;
    }
}
