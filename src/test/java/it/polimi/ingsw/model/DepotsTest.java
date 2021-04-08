package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepotsTest {

    @Test
    void goodSetup(){

        Depots depots = new Depots();

        assertTrue(depots.getResources().isEmpty());

        try{
            depots.subResource(new NumberOfResources(0, 0, 3, 1));
            fail();
        }catch(OutOfResourcesException ignored){}

        assertTrue(depots.getResources().isEmpty());

        assertEquals(depots.getVictoryPoints(), 0);

        assertTrue(depots.match(new NumberOfResources()));

        assertFalse(depots.match(new NumberOfResources(1, 0,0, 0)));

        try{
            depots.addResourcesFromMarket(new NumberOfResources(0, 0, 0, 4));
            fail();
        }catch(UnableToFillException ignored){}
    }

    @Test
    void operationTest(){

        Depots depots= new Depots();

        depots.addResourceFromProduction(new NumberOfResources(100, 100, 100, 100));

        assertFalse(depots.getResources().isEmpty());

        assertEquals(new NumberOfResources(100, 100, 100, 100), depots.getResources());

        try{
            depots.subResource(new NumberOfResources(50, 50, 50, 50));
            depots.subResource(new NumberOfResources(50, 50, 50, 50));
        }catch(OutOfResourcesException e){
            fail();
        }

        assertTrue(depots.getResources().isEmpty());
    }
}