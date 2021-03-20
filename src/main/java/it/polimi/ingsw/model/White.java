package it.polimi.ingsw.model;

import java.util.*;

public class White extends Ability {

    private ResourceType typeOfRes;

    /*Default constructor*/
    private White() {
    }

    /*Getter*/
    public ResourceType getTypeOfRes() {
        return this.typeOfRes;
    }

    /*Abstract class to implement*/
    public void RunAbility() {

    }
}