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

    /**
     *
     * @return
     */
    public ColorDevCard getDiscountCardColor() {
        return cardColor;
    }

    /**
     *
     * @return
     */
    public int getDiscountAmount() {
        return discount;
    }
}