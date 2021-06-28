package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.server.parse.Starter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
class DevelopmentCardTest {

    @Test
    void easyTest(){
        ProductionPowerExt power = new ProductionPowerExt(0, new NumberOfResources(0,1,2,3), new NumberOfResources(4,5,6,7));
        NumberOfResources cost = new NumberOfResources(3,3,1,5);
        DevelopmentCardExt card = new DevelopmentCardExt(Level.ONE, ColorDevCard.BLUE, cost, power, 3);

        assertEquals(Level.ONE, card.getLevel());
        assertEquals(ColorDevCard.BLUE, card.getColor());
        assertEquals(cost, card.getCost());
        assertEquals(power, card.getProductionPower());
        assertEquals(3, card.getVictoryPoints());
    }

    @Test
    void cliTest(){
        ArrayList<DevelopmentCardExt> developmentCards= new ArrayList<>();
        developmentCards= Starter.DevCardParser();

        DashboardExt dashboard= new DashboardExt(developmentCards);

        System.out.println(dashboard);
    }

}