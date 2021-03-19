package it.polimi.ingsw.model;

import java.util.*;

/**
 * 
 */
public class DevelopmentCard extends Card {

    /**
     * Default constructor
     */
    public DevelopmentCard() {
    }

    /**
     * 
     */
    private Level level;

    /**
     * 
     */
    private ColorDevCard cardColor;

    /**
     *
     */
    private NumberOfResources cost;

    private ProductionPower productionPower;

    /**
     * @return
     */
    public NumberOfResources getCost() {
        return cost;
    }

    /**
     * @return
     */
    public ProductionPower getProductionPower() {
        return productionPower;
    }

    /**
     * @return
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @return
     */
    public ColorDevCard getColor() {
        return cardColor;
    }

}