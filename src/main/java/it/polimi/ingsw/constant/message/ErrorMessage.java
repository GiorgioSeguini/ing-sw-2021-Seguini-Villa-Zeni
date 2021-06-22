package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

public class ErrorMessage implements Message{

    public static String className = "ErrorMessage";

    private final int myId;

    public ErrorMessage(int myId) {
        this.myId = myId;
    }

    @Override
    public void handleMessage(Client client) {
    if(client.getSimpleGame().getMyID()==myId)
        client.getUI().update();
    }

    @Override
    public String getName() {
        return className;
    }
}
