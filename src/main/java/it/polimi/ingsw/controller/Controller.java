package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
import it.polimi.ingsw.observer.Observer;

public class Controller implements Observer<MoveType> {

    private final Game game;

    public Controller(Game game) {
        this.game = game;
    }

    @Override
    public void update(MoveType x) {
        if(x.canPerform(game)) {
            x.getPlayer().setErrorMessage(ErrorMessage.NoError);
            x.performMove(game);
        }else{
            //x.getPlayer().setErrorMessage();
        }
    }

}
