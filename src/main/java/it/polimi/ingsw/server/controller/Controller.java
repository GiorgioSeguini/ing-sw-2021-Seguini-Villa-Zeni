package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.observer.Observer;

public class Controller implements Observer<Performable> {

    private final GameExt game;

    public Controller(GameExt game) {
        this.game = game;
    }

    @Override
    public synchronized void update(Performable x) {
        if(x.canPerform(game)) {
            x.performMove(game);
        }else{
            System.out.println("Move invalid");
            //x.getPlayer().setErrorMessage();
        }
    }

}
