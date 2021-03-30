package it.polimi.ingsw.model;

/*Last Edit: Gio*/

import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;

/**
 * 
 */
public class DevelopmentCard extends Card {

    private Level level;
    private ColorDevCard cardColor;
    private NumberOfResources cost;
    private ProductionPower productionPower;

    /**
     * Default constructor
     */
    public DevelopmentCard(Level level, ColorDevCard cardColor, NumberOfResources cost, ProductionPower productionPower) {
        this.level = level;
        this.cardColor = cardColor;
        this.cost = cost;
        this.productionPower = productionPower;
    }

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