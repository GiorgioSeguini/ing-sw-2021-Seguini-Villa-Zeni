package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ResourceType;

import javax.swing.text.View;
import java.util.ArrayList;

public class MoveWhiteConversion extends MoveType{
    private final ArrayList<ResourceType> whitemarbles;

    public MoveWhiteConversion(Player player, View view, boolean isLastMove, ArrayList<ResourceType> whitemarbles){
        super(player,view,isLastMove);
        this.whitemarbles=whitemarbles;
    }

    public ArrayList<ResourceType> getWhitemarbles() {
        return whitemarbles;
    }
}
