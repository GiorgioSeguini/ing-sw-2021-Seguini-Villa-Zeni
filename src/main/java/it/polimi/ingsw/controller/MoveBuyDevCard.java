package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;

import javax.swing.text.View;

public class MoveBuyDevCard extends MoveType{

    private final int pos;
    DevelopmentCard cardToBuy;

    public MoveBuyDevCard(Player active, View view, boolean isLastMove, int pos, DevelopmentCard cardToBuy) {
        super(active, view, isLastMove);
        this.pos = pos;
        this.cardToBuy = cardToBuy;
    }

    public int getPos() {
        return pos;
    }

    public DevelopmentCard getCardToBuy() {
        return cardToBuy;
    }
}
