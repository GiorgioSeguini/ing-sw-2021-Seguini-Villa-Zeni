package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.Game;

public class InitialMessage implements Message{

    public static final String className = "InitialMessage";

    private final String model;

    public InitialMessage(String model) {
        this.model = model;
    }

    @Override
    public void handleMessage(Game simpleGame) {
        //TODO parser
        //simpleGame = new Game();
    }

    @Override
    public String getName() {
        return className;
    }
}
