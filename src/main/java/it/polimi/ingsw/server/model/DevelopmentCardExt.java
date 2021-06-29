package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;

/**
 * Development card Ext class.
 * Extends DevelopmentCard.
 */
public class DevelopmentCardExt extends DevelopmentCard {

    /**
     * Instantiates a new Development card ext.
     *
     * @param level of type Level: the level of the card.
     * @param cardColor of type ColorDevCard: the card color of the card.
     * @param cost of type NumberOfResources: the cost of the card.
     * @param productionPower of type ProductionPowerExt: the production power of the card.
     * @param victoryPoints of type int: the victory points of the card.
     */
    public DevelopmentCardExt(Level level, ColorDevCard cardColor, NumberOfResources cost, ProductionPowerExt productionPower, int victoryPoints) {
        super(level, cardColor, cost, productionPower, victoryPoints);
    }

    /**
     * @return a ProductionPowerExt
     */
    @Override
    public ProductionPowerExt getProductionPower() {
        return (ProductionPowerExt) super.getProductionPower();
    }

}
