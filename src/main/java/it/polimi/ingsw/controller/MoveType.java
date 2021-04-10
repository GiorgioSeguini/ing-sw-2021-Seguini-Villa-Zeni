package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Player;

import javax.swing.text.View;

public class MoveType {
    //raccogliere tutte le info chieste al giocatore e chiama l'update del controller

    Player player;
    View view;
    boolean isLastMove;

    public MoveType(Player player, View view, boolean isLastMove){
        this.player = player;
        this.view=view;
        this.isLastMove=isLastMove;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }

    public boolean isLastMove() {
        return isLastMove;
    }

}
