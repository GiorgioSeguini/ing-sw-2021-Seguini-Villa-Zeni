package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.server.observer.Observer;
import it.polimi.ingsw.server.model.Game;

public class Controller implements Observer<MoveType> {

    private final Game game;

    public Controller(Game game) {
        this.game = game;
    }

    @Override
    public synchronized void update(MoveType x) {
        if(x.canPerform(game)) {
            x.getPlayer().setErrorMessage(ErrorMessage.NoError);
            x.performMove(game);
        }else{
            //x.getPlayer().setErrorMessage();
        }
    }

}
