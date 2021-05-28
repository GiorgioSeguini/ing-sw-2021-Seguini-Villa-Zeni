package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

public class RejectMessage implements Message{
    public static String className = "RejectMessage";

    @Override
    public void handleMessage(Client client) {
        //TODO
    }

    @Override
    public String getName() {
        return className;
    }
}
