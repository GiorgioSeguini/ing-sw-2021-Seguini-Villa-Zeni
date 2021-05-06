package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;

/**
 * This interface is the generic message send from server to client
 */
public interface Message {

    /**
     * this method trigger the changement of simplify model on client-side
     * @param client reference to the client
     */
    void handleMessage(Client client);

    /**
     *
     * @return the class name, useful for json serialization
     */
    String getName();
}
