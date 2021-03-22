package it.polimi.ingsw.model;

import java.util.*;

public class Discount extends Ability{

    private ColorDevCard cardColor;
    private int discount;

    /*Default constructor*/
    public Discount() {
    }

    /*Getter*/
    public ColorDevCard getDiscountCardColor() {
        return cardColor;
    }

    public int getDiscountAmount() {
        return discount;
    }

    /*Abstract class to implement*/
    public void RunAbility(){

    }

}