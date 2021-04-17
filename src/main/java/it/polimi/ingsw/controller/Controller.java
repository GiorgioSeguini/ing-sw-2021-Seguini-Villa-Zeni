package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.ErrorMessage;

public class Controller{

    private Game game;

    public void update(MoveType x) {
        if(x.canPerform(game)) {
            x.getPlayer().setErrorMessage(ErrorMessage.NoError);
            x.performMove(game);
        }else{
            //x.getPlayer().setErrorMessage();
        }
    }

}
