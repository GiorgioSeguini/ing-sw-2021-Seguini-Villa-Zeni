package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductionPower;

import javax.swing.text.View;

public class MoveActiveProduction extends MoveType{

    ProductionPower[] toActive;

    public MoveActiveProduction(Player player, View view, boolean isLastMove, ProductionPower[] toActive) {
        super(player, view, isLastMove);
        this.toActive = toActive;
    }

}
