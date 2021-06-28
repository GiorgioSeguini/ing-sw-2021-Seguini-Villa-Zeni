package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

public class RejectMessage implements Message{
    public static final String className = "RejectMessage";

    @Override
    public void handleMessage(Client client) {
        client.getUI().update();
    }

    @Override
    public String getName() {
        return className;
    }
}
