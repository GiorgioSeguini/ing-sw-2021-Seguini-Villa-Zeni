package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;

import javax.swing.text.View;

public class MoveDiscardResources extends MoveType{
    private final NumberOfResources toDiscard;

    public MoveDiscardResources(Player player, View view, boolean isLastMove, NumberOfResources toDiscard){
        super(player,view, isLastMove);
        this.toDiscard=toDiscard;
    }

    public NumberOfResources getToDiscard() {
        return toDiscard;
    }
}
