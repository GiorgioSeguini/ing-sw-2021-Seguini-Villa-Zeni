package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

public class AcceptMessage implements Message{
    public static String className = "AcceptMessage";

    @Override
    public void handleMessage(Client client) {
            client.getUI().setActive();
            client.getUI().update();
    }

    @Override
    public String getName() {
        return className;
    }
}
