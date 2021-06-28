package it.polimi.ingsw.server.observer;

import java.util.ArrayList;
import java.util.List;

public interface Observable<T> {

    void addObserver(Observer<T> observer);

    void notify(T message);

}
