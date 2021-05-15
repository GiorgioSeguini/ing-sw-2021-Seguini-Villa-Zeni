package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.model.Game;

public interface Performable {
    void performMove(Game game);

    public boolean canPerform(Game game);
}
