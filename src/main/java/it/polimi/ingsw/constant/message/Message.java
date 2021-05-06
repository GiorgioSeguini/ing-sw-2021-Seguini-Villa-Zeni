package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.Game;

/**
 * This interface is the generic message send from server to client
 */
public interface Message {

    /**
     * this method trigger the changement of simplify model on client-side
     * @param simpleGame reference to the simpler model
     */
    void handleMessage(Game simpleGame);

    /**
     *
     * @return the class name, useful for json serialization
     */
    String getName();
}
