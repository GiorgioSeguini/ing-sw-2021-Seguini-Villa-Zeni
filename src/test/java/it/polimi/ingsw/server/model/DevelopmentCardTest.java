package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.ProductionPower;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardTest {

    @Test
    void easyTest(){
        ProductionPower power = new ProductionPower(0, new NumberOfResources(0,1,2,3), new NumberOfResources(4,5,6,7));
        NumberOfResources cost = new NumberOfResources(3,3,1,5);
        DevelopmentCard card = new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, cost, power, 3);

        assertEquals(Level.ONE, card.getLevel());
        assertEquals(ColorDevCard.BLUE, card.getColor());
        assertEquals(cost, card.getCost());
        assertEquals(power, card.getProductionPower());
        assertEquals(3, card.getVictoryPoints());


    }

}