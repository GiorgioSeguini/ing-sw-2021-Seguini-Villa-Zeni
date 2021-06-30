package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.observer.Observer;

/**
 * Interface ClientConnection
 */
public interface ClientConnection{

    /**
     * Methods that close the connection
     */
    void closeConnection();

    /**
     * Method that add an observer.
     * @param observer
     */
    void addObserver(Observer<String> observer);

    /**
     * Method that send a json.
     * @param json
     */
    void send(String json);
}
