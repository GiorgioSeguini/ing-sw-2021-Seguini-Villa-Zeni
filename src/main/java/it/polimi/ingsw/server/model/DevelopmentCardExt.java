package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;

public class DevelopmentCardExt extends DevelopmentCard {

    public DevelopmentCardExt(Level level, ColorDevCard cardColor, NumberOfResources cost, ProductionPowerExt productionPower, int victoryPoints) {
        super(level, cardColor, cost, productionPower, victoryPoints);
    }
}
