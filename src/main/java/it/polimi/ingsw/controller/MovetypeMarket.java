package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.MoveChoose;
import it.polimi.ingsw.model.enumeration.ResourceType;

import javax.swing.text.View;
import java.util.ArrayList;

public class MovetypeMarket extends MoveType{
    private ArrayList<ResourceType> whitemarbles;
    private int indextobuy;
    private NumberOfResources todiscard;

    public MovetypeMarket(Player player, View view, boolean isLastMove, NumberOfResources todiscard){
        super(player,view, MoveChoose.BuyFromMarket,isLastMove);
        this.todiscard=todiscard;
    }

    public MovetypeMarket(Player player, View view, boolean isLastMove, int indextobuy){
        super(player,view, MoveChoose.BuyFromMarket,isLastMove);
        this.indextobuy=indextobuy;
    }

    public MovetypeMarket(Player player, View view, boolean isLastMove, ArrayList<ResourceType> whitemarbles, int indextobuy){
        super(player,view,MoveChoose.BuyFromMarket,isLastMove);
        this.whitemarbles=whitemarbles;
        this.indextobuy=indextobuy;
    }

    public ArrayList<ResourceType> getWhitemarbles() {
        return whitemarbles;
    }

    public int getIndextobuy() {
        return indextobuy;
    }

    public NumberOfResources getTodiscard() {
        return todiscard;
    }
}
