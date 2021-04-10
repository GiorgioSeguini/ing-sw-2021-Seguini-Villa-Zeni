package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductionPower;
import it.polimi.ingsw.model.enumeration.*;

import javax.swing.text.View;

public class MoveActiveProduction extends MoveType{

    ProductionPower[] toActive;

    public MoveActiveProduction(Player player, View view, boolean isLastMove, ProductionPower[] toActive) {
        super(player, view, MoveChoose.ActivateProduction, isLastMove);
        this.toActive = toActive;
    }

}
