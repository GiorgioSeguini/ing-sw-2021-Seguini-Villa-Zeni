package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

public class LastMessage implements Message{

    public static final String className = "LastMessage";

    @Override
    public void handleMessage(Client client) {
        client.getUI().update();
    }

    @Override
    public String getName() {
        return className;
    }
}
