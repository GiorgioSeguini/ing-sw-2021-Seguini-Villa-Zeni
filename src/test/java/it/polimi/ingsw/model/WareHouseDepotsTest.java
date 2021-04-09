package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WareHouseDepotsTest {

    @Test
    void getResources(){
        WareHouseDepots test= new WareHouseDepots();
        assertEquals(test.getResources(),new NumberOfResources());


    }
}
