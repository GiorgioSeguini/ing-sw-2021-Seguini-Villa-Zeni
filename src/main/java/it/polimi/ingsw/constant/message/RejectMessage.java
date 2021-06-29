package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

/**
 * RejectMessage class.
 * Implements Message interface.
 * Manage the rejecting messages.
 */
public class RejectMessage implements Message{

    public static final String className = "RejectMessage";

    /**
     * Handle the rejecting messages and update the client.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
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
