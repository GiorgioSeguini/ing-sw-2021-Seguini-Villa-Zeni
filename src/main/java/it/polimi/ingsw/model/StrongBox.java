package it.polimi.ingsw.model;

import java.util.*;

public class StrongBox {

    private NumberOfResources resources = new NumberOfResources();

    /* Default constructor*/
    public StrongBox() {
    }

    /*Getter*/
    public NumberOfResources getResources() {
        return null;
    }
    //costruttore mancante

    /*Additional Methods*/
    public void addResource(NumberOfResources input) {
        this.resources=this.resources.add(input);
    }/**This method adds resources to the strongbox*/

    public void subResource(NumberOfResources required) {
        this.resources=this.resources.sub(required);
    }/**This method adds resources to the strongbox*/

}