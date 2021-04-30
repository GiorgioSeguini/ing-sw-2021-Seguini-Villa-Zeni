package it.polimi.ingsw.server;

import it.polimi.ingsw.server.observer.Observer;
import org.json.simple.JSONObject;

public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<JSONObject> observer);

    void asyncSend(Object message);
}
