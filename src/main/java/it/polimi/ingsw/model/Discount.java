package it.polimi.ingsw.model;

import java.util.*;

/**
 * 
 */
public class Discount extends Ability{

    /**
     * Default constructor
     */
    public Discount() {
    }

    /**
     * 
     */
    private ColorDevCard cardColor;

    /**
     * 
     */
    private int discount;

    public ColorDevCard getDiscountCardColor() {
        return cardColor;
    }

    public int getDiscountAmount() {
        return discount;
    }
}