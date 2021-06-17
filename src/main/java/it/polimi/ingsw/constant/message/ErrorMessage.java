package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

public class ErrorMessage implements Message{

    public static String className = "ErrorMessage";

    @Override
    public void handleMessage(Client client) {
        client.getUI().update();
    }

    @Override
    public String getName() {
        return className;
    }
}
