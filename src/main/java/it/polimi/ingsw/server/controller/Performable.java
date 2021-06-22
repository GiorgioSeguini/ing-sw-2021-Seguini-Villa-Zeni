package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.GameExt;

public interface Performable {
    void performMove(GameExt game);

    boolean canPerformExt(GameExt game);

    int getIdPlayer();
}
