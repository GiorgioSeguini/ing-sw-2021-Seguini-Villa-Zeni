package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

/**
 * AcceptMessage class.
 * Implements Message interface.
 * @see it.polimi.ingsw.constant.message.Message
 */
public class AcceptMessage implements Message{
    /**
     * The constant className.
     */
    public static final String className = "AcceptMessage";
    private final String roomName;

    /**
     * Instantiates a new Accept message.
     *
     * @param roomName the room name
     */
    public AcceptMessage(String roomName) {
        this.roomName=roomName;
    }

    /**
     *
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
            client.getUI().setActive();
            client.setRoomName(roomName);
            client.getUI().update();
    }

    /**
     *
     * @return of type String: the class name, useful for json serialization.
     */
    @Override
    public String getName() {
        return className;
    }
}
