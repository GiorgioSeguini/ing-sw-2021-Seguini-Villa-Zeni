package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.server.observer.Observer;

public class Controller implements Observer<MoveType> {

    private final Game game;

    public Controller(Game game) {
        this.game = game;
    }

    @Override
    public synchronized void update(MoveType x) {
        if(x.canPerform(game)) {
            x.performMove(game);
        }else{
            System.out.println("Move invalid");
            //x.getPlayer().setErrorMessage();
        }
    }

}
