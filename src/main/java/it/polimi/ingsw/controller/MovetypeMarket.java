package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ResourceType;

import javax.swing.text.View;
import java.util.ArrayList;

public class MovetypeMarket extends MoveType{

    private final int indextobuy;

    public MovetypeMarket(Player player, View view, boolean isLastMove, int indextobuy){
        super(player, view, isLastMove);
        this.indextobuy=indextobuy;
    }

    public int getIndextobuy() {
        return indextobuy;
    }

}
