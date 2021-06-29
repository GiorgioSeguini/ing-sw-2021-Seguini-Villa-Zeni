package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
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
        ProductionPowerExt power0 = new ProductionPowerExt();

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

        PlayerExt owner = new PlayerExt("Pippo");
        try {
            owner.getDepots().addResourcesFromMarket(res1);
        } catch (UnableToFillException e) {
            fail();
        }

        RequirementsExt test = new RequirementsExt(res1);
        RequirementsExt test2 = new RequirementsExt(req1);

        //getter test
        assertEquals(res1, test.getNumberOfResources());
        assertEquals(1, test2.getReq(ColorDevCard.BLUE, Level.ONE));

        //equality test
        assertNotEquals(test, test2);
        assertNotEquals(test, new RequirementsExt());

        assertFalse(test.match(new PlayerExt("Gio")));
        assertTrue(test.match(owner));
        assertFalse(test2.match(owner));
        try {
            owner.getPersonalBoard().addDevCard(new DevelopmentCardExt(Level.ONE, ColorDevCard.GREEN, res0, power0, 0), 0);
            owner.getPersonalBoard().addDevCard(new DevelopmentCardExt(Level.TWO, ColorDevCard.BLUE, res0, power0, 0), 0);
        } catch (NoSpaceException e) {
            fail();
        }
        assertTrue(test2.match(owner));

    }


    @Test
    public void test2(){

        //empty constructor test
        RequirementsExt requirements = new RequirementsExt();
        assertEquals(new NumberOfResources(), requirements.getNumberOfResources());

        for(int[] i: requirements.getMinNumber()){
            for(int j : i){
                assertEquals(0, j);
            }
        }

        assertEquals(requirements, requirements);
        assertNotEquals(requirements, new Object());
    }
}