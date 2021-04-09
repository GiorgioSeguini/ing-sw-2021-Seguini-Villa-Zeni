package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ResourceType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WareHouseDepotsTest {

    @Test
    void getResourcesandAddExtraShelf(){
        WareHouseDepots test= new WareHouseDepots();
        assertEquals(test.getResources(),new NumberOfResources());
        Shelf x= new Shelf(ResourceType.Shields,0);
        x.setUsed(3);
        test.addExtraShelf(x);
        assertEquals(test.getResources(), new NumberOfResources(0,3,0,0));
        x= new Shelf(ResourceType.Coins,0);
        x.setUsed(2);
        test.addExtraShelf(x);
        assertEquals(test.getResources(), new NumberOfResources(0,3,2,0));
        x= new Shelf(ResourceType.Servants,0);
        x.setUsed(4);
        test.addExtraShelf(x);
        assertEquals(test.getResources(), new NumberOfResources(4,3,2,0));
        x= new Shelf(ResourceType.Stones,0);
        x.setUsed(1);
        test.addExtraShelf(x);
        assertEquals(test.getResources(), new NumberOfResources(4,3,2,1));
        x= new Shelf(ResourceType.Stones,0);
        x.setUsed(1);
        test.addExtraShelf(x);
        assertEquals(test.getResources(), new NumberOfResources(4,3,2,2));
        test.addExtraShelf(new Shelf(1));
        assertEquals(test.getResources(), new NumberOfResources(4,3,2,2));

    }
}
