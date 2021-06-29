package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

/**
 * This interface is the generic message send from server to client.
 */
public interface Message {

    /**
     * This method trigger the changement of simplify model on client-side.
     * @param client of type Client: reference to the client.
     */
    void handleMessage(Client client);

    /**
     *
     * @return of type String: the class name, useful for json serialization.
     */
    String getName();
}
