package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;

import javax.swing.text.View;

public class MoveChoseResources extends MoveType{
     private final NumberOfResources ofYourChoiceInput;
    private final NumberOfResources ofYourChoiceOutput;

    public MoveChoseResources(Player active, View view, boolean isLastMove, NumberOfResources ofYourChoiceInput, NumberOfResources ofYourChoiceOutput) {
        super(active, view, isLastMove);
        this.ofYourChoiceInput = ofYourChoiceInput;
        this.ofYourChoiceOutput = ofYourChoiceOutput;
    }

    public NumberOfResources getOfYourChoiceInput() {
        return ofYourChoiceInput;
    }

    public NumberOfResources getOfYourChoiceOutput() {
        return ofYourChoiceOutput;
    }
}
