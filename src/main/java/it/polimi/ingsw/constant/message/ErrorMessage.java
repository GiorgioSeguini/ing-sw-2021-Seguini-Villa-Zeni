package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

/**
 * ErrorMessage class.
 * Implements Message interface.
 * Manage the error messages.
 */
public class ErrorMessage implements Message{

    public static final String className = "ErrorMessage";

    private final int myId;

    /**
     * Instantiates a new Error message.
     *
     * @param myId of type int: the id.
     */
    public ErrorMessage(int myId) {
        this.myId = myId;
    }

    /**
     * Handle the error messages and update the client.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
    if(client.getSimpleGame().getMyID()==myId)
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
