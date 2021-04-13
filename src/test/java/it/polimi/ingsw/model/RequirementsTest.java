package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.exception.NoSpaceException;
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

        Player owner = new Player("Pippo", new PersonalBoard(new LeaderCard[2]), 0, res1);

        Requirements test = (new Requirements(res1));
        Requirements test2 = new Requirements(req1);
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
}