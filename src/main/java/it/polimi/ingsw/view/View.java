package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MoveType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public abstract class View extends Observable<MoveType> /*implements Observer<MoveMessage> */{

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    protected abstract void showMessage(Object message);

    void handleMove(MoveType x) {
        notify(x);
    }

    public void reportError(String message){
        showMessage(message);
    }

}
