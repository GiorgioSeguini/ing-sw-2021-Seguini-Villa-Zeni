package it.polimi.ingsw.server;

import it.polimi.ingsw.server.observer.Observer;

public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<String> observer);

    void send(String json);
}
