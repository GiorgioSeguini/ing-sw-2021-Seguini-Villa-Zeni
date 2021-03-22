package it.polimi.ingsw.model;

import java.util.*;

public class StrongBox {

    private NumberOfResources resources;

    /* Default constructor*/
    StrongBox() {
        resources=new NumberOfResources(0,0,0,0);
    }

    /*Getter*/
    public NumberOfResources getResources() {
        return resources;
    }

    /*Additional Methods*/
    public void addResource(NumberOfResources input) {
        resources = resources.add(input);
    }/**This method adds resources to the strongbox*/

    public void subResource(NumberOfResources required) {
        resources=resources.sub(required);
    }/**This method subs resources to the strongbox*/

}