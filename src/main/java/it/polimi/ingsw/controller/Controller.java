package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

public class Controller{

    private Game game;

    public void update(MoveType x) {
        x.performMove(game);
    }

}
