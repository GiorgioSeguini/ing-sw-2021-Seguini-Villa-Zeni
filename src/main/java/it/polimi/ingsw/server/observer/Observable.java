package it.polimi.ingsw.server.observer;

import java.util.ArrayList;
import java.util.List;

public interface Observable<T> {

    //private transient final List<Observer<T>> observers = new ArrayList<>();

    //void addObserver(Observer<T> observer);
      /*  synchronized (observers) {
            observers.add(observer);
        }
    }*/

    //void removeObserver(Observer<T> observer);
    /*    synchronized (observers) {
            observers.remove(observer);
        }
    }*/

    default void notify(T message){
        return;
    }
    /*    synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }*/

}
