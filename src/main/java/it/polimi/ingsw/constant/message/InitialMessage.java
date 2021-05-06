package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.parser.StarterClient;

public class InitialMessage implements Message{

    public static final String className = "InitialMessage";

    private final String model;

    public InitialMessage(String model) {
        this.model = model;
    }

    @Override
    public void handleMessage(Client client){
        client.setSimpleGame(StarterClient.fromJsonGame(model));
    }

    @Override
    public String getName() {
        return className;
    }
}
