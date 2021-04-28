package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.MoveType;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.observer.Observable;

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