package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

public class AcceptMessage implements Message{
    public static final String className = "AcceptMessage";
    private final String roomName;

    public AcceptMessage(String roomName) {
        this.roomName=roomName;
    }

    @Override
    public void handleMessage(Client client) {
            client.getUI().setActive();
            client.setRoomName(roomName);
            client.getUI().update();
    }

    @Override
    public String getName() {
        return className;
    }
}
