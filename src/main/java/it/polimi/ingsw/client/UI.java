package it.polimi.ingsw.client;

import it.polimi.ingsw.constant.message.ConnectionMessage;

/**
 * The interface Ui.
 */
public interface UI {
    /**
     * Update.
     */
    void update();

    /**
     * Sets active.
     */
    void setActive();

    /**
     * Print connection message.
     *
     * @param message of type ConnectionMessage: the message.
     */
    void printConnectionMessage(ConnectionMessage message);
}
