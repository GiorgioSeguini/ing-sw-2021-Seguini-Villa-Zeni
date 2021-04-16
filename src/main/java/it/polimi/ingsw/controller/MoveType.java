package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import javax.swing.text.View;

public abstract class MoveType {
    //raccogliere tutte le info chieste al giocatore e chiama l'update del controller

    Player player;

    public MoveType(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean performMove(Game game){
        return false;
    }

}
