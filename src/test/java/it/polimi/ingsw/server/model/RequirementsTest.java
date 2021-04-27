package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.enumeration.ColorDevCard;
import it.polimi.ingsw.server.model.enumeration.Level;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequirementsTest {

    @Test
    public void test(){
        NumberOfResources res0 = new NumberOfResources();
        NumberOfResources res1 = new NumberOfResources(0, 2, 2, 1);
        NumberOfResources res2 = new NumberOfResources( 0, 3, 1, 2);
        ProductionPower power0 = new ProductionPower();

        ArrayList<Map.Entry<ColorDevCard, Level>> req1 = new ArrayList<>();
        req1.add(new Map.Entry<ColorDevCard, Level>() {
            @Override
            public ColorDevCard getKey() {
                return ColorDevCard.BLUE;
            }

            @Override
            public Level getValue() {
                return Level.ONE;
            }

            @Override
            public Level setValue(Level value) {
                return null;
            }
        });

        Player owner = new Player("Pippo");
        try {
            owner.getDepots().addResourcesFromMarket(res1);
        } catch (UnableToFillException e) {
            fail();
        }

        Requirements test = new Requirements(res1);
        Requirements test2 = new Requirements(req1);

        //getter test
        assertEquals(res1, test.getNumberOfResourceses());
        assertEquals(1, test2.getReq(ColorDevCard.BLUE, Level.ONE));

        //equality test
        assertNotEquals(test, test2);
        assertNotEquals(test, new Requirements());

        assertFalse(test.match(new Player("Gio")));
        assertTrue(test.match(owner));
        assertFalse(test2.match(owner));
        try {
            owner.getPersonalBoard().addDevCard(new DevelopmentCard(Level.ONE, ColorDevCard.GREEN, res0, power0, 0), 0);
            owner.getPersonalBoard().addDevCard(new DevelopmentCard(Level.TWO, ColorDevCard.BLUE, res0, power0, 0), 0);
        } catch (NoSpaceException e) {
            fail();
        }
        assertTrue(test2.match(owner));

    }


    @Test
    public void test2(){

        //empty constructor test
        Requirements requirements = new Requirements();
        assertEquals(new NumberOfResources(), requirements.getNumberOfResourceses());

        for(int[] i: requirements.getMinNumber()){
            for(int j : i){
                assertEquals(0, j);
            }
        }

        assertEquals(requirements, requirements);
        assertNotEquals(requirements, new Object());
    }
}