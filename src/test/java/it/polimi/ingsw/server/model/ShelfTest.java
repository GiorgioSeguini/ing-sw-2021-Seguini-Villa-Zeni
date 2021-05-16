package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.Shelf;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ShelfTest {

    @Test
    void getResType(){
        Shelf test= new Shelf(ResourceType.Shields,1);
        Shelf test2= new Shelf(ResourceType.Coins,0);
        Shelf test3= new Shelf(ResourceType.Servants,2);
        Shelf test4= new Shelf(ResourceType.Stones,3);

        assertEquals(test.getResType(),ResourceType.Shields);
        assertEquals(test2.getResType(),ResourceType.Coins);
        assertEquals(test3.getResType(),ResourceType.Servants);
        assertEquals(test4.getResType(),ResourceType.Stones);
        if(test.getResType()==ResourceType.Coins){
            fail();
        }

        test=new Shelf(1);
        test2=new Shelf(2);
        test3= new Shelf(3);
        assertNull(test.getResType());
        assertNull(test2.getResType());
        assertNull(test3.getResType());
    }

    @Test
    void getMaxSize(){
        Shelf test= new Shelf(1);
        Shelf test2=new Shelf(2);
        Shelf test3=new Shelf(3);
        Shelf test4=new Shelf(500);

        assertEquals(test.getMaxSize(),1);
        assertEquals(test2.getMaxSize(),2);
        assertEquals(test3.getMaxSize(),3);
        assertEquals(test4.getMaxSize(),500);
    }

    @Test
    void setgetIsextra(){
        Shelf test=new Shelf(0);
        test.setIsExtra();
        assertTrue(test.getIsExtra());
        test =new Shelf(0);
        assertFalse(test.getIsExtra());
    }

    @Test
    void setgetUsed(){
        Shelf test=new Shelf(0);
        assertEquals(test.getUsed(),0);
        test.setUsed(3);
        assertEquals(test.getUsed(),3);
    }

    @Test
    void setResType(){
        Shelf test=new Shelf(1);

        test.setResType(ResourceType.Stones);
        assertEquals(test.getResType(),ResourceType.Stones);
        test.setResType(ResourceType.Servants);
        assertEquals(test.getResType(),ResourceType.Servants);
        test.setResType(ResourceType.Coins);
        assertEquals(test.getResType(),ResourceType.Coins);
        test.setResType(ResourceType.Shields);
        assertEquals(test.getResType(),ResourceType.Shields);
    }

    @Test
    void exception(){
        try{
            new Shelf(-1);
            fail();
        }catch(IllegalArgumentException ignored){}

        Shelf shelf = new Shelf(0);
        try {
            shelf.setUsed(-1);
            fail();
        }catch(IllegalArgumentException ignored){}
    }

}
