package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.MoveChoose;

import javax.swing.text.View;

public class MoveType {
    //raccogliere tutte le info chieste al giocatore e chiama l'update del controller

    Player active;
    View view;
    boolean isLastMove;
    MoveChoose move;

    public MoveType(Player active, View view, MoveChoose move, boolean isLastMove){
        this.active=active;
        this.view=view;
        this.isLastMove=isLastMove;
        this.move=move;
    }

    public Player getActive() {
        return active;
    }

    public View getView() {
        return view;
    }

    public boolean isLastMove() {
        return isLastMove;
    }

    public MoveChoose getMove() {
        return move;
    }
}
