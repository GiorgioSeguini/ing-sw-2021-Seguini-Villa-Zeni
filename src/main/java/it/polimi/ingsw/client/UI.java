package it.polimi.ingsw.client;

import it.polimi.ingsw.constant.message.ConnectionMessage;

public interface UI {
    void update();

    void setActive();

    void printConnectionMessage(ConnectionMessage message);
}
