package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import it.polimi.ingsw.model.exception.UnableToFillException;
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

    @Test
    public void addResourceAndCleanTest(){
        WareHouseDepots warehouse= new WareHouseDepots();

        try {
            warehouse.addResource(new NumberOfResources(2,2,3,0));
            fail();
        } catch (UnableToFillException e) {}
        assertEquals(new NumberOfResources(), warehouse.getResources());

        try {
            warehouse.addResource(new NumberOfResources(1,2,3,0));
        } catch (UnableToFillException e) {
            fail();
        }

        assertEquals(new NumberOfResources(1,2,3,0),warehouse.getResources());
        int i=0;
        for(Shelf x: warehouse.getShelfs()){
            i++;
            assertFalse(x.getIsExtra());
            assertNotNull(x.getResType());
            assertEquals(i,x.getUsed());
        }

        try {
            warehouse.addResource(new NumberOfResources(1,0,0,0));
            fail();
        } catch (UnableToFillException e) {}
        assertEquals(new NumberOfResources(1,2,3,0), warehouse.getResources());

        warehouse.CleanShelf();
        assertEquals(new NumberOfResources(),warehouse.getResources());
        i=0;
        for(Shelf x: warehouse.getShelfs()){
            i++;
            assertFalse(x.getIsExtra());
            assertNull(x.getResType());
            assertEquals(0,x.getUsed());
            assertEquals(i,x.getMaxSize());
        }

        try {
            warehouse.addResource(new NumberOfResources(1,1,1,1));
            fail();
        } catch (UnableToFillException e) {}
        assertEquals(new NumberOfResources(), warehouse.getResources());

        try {
            warehouse.addResource(new NumberOfResources(-1,2,3,0));
            fail();
        } catch (UnableToFillException|IllegalArgumentException e) {}
        assertEquals(new NumberOfResources(), warehouse.getResources());

        try {
            warehouse.addResource(new NumberOfResources(0,0,4,0));
            fail();
        } catch (UnableToFillException|IllegalArgumentException e) {}
        assertEquals(new NumberOfResources(), warehouse.getResources());

        warehouse.addExtraShelf(new Shelf(ResourceType.Coins,1));
        assertEquals(4,warehouse.getShelfs().size());
        try {
            warehouse.addResource(new NumberOfResources(0,0,4,0));
        } catch (UnableToFillException|IllegalArgumentException e) {
            fail();
        }
        assertEquals(new NumberOfResources(0,0,4,0), warehouse.getResources());

        try{
            for (i=0; i<warehouse.getShelfs().size();i++){
                if(i<2){
                    assertNull(warehouse.getShelfs().get(i).getResType());
                    assertEquals(0,warehouse.getShelfs().get(i).getUsed());
                    assertFalse(warehouse.getShelfs().get(i).getIsExtra());
                }
                if(i==2){
                    assertEquals(ResourceType.Coins,warehouse.getShelfs().get(i).getResType());
                    assertEquals(3,warehouse.getShelfs().get(i).getUsed());
                    assertFalse(warehouse.getShelfs().get(i).getIsExtra());
                }
                if(i>2){
                    assertEquals(ResourceType.Coins,warehouse.getShelfs().get(i).getResType());
                    assertEquals(1,warehouse.getShelfs().get(i).getUsed());
                    assertTrue(warehouse.getShelfs().get(i).getIsExtra());
                }
            }

        }catch (NullPointerException e){
            fail();
        }

        warehouse.CleanShelf();
        assertEquals(new NumberOfResources(),warehouse.getResources());
        i=0;
        for(Shelf x: warehouse.getShelfs()){
            if(i>2){
                assertTrue(x.getIsExtra());
                assertNotNull(x.getResType());
            }
            else{
                assertFalse(x.getIsExtra());
                assertNull(x.getResType());
                assertEquals(0,x.getUsed());
            }
            i++;
        }

        warehouse.addExtraShelf(new Shelf(ResourceType.Coins,1));

        try {
            warehouse.addResource(new NumberOfResources(0,0,5,0));
        } catch (UnableToFillException e) {
            fail();
        }
        assertEquals(new NumberOfResources(0,0,5,0),warehouse.getResources());

        try{
            for (i=0; i<warehouse.getShelfs().size();i++){
                if(i<2){
                    assertNull(warehouse.getShelfs().get(i).getResType());
                    assertEquals(0,warehouse.getShelfs().get(i).getUsed());
                    assertFalse(warehouse.getShelfs().get(i).getIsExtra());
                }
                if(i==2){
                    assertEquals(ResourceType.Coins,warehouse.getShelfs().get(i).getResType());
                    assertEquals(3,warehouse.getShelfs().get(i).getUsed());
                    assertFalse(warehouse.getShelfs().get(i).getIsExtra());
                }
                if(i>2){
                    assertEquals(ResourceType.Coins,warehouse.getShelfs().get(i).getResType());
                    assertEquals(1,warehouse.getShelfs().get(i).getUsed());
                    assertTrue(warehouse.getShelfs().get(i).getIsExtra());
                }
            }

        }catch (NullPointerException e){
            fail();
        }

        warehouse.CleanShelf();
        assertEquals(new NumberOfResources(),warehouse.getResources());
        i=0;
        for(Shelf x: warehouse.getShelfs()){
            if(i>2){
                assertTrue(x.getIsExtra());
                assertNotNull(x.getResType());
            }
            else{
                assertFalse(x.getIsExtra());
                assertNull(x.getResType());
                assertEquals(0,x.getUsed());
            }
            i++;
        }

        warehouse=new WareHouseDepots();
        warehouse.addExtraShelf(new Shelf(ResourceType.Stones, 2));
        warehouse.addExtraShelf(new Shelf(ResourceType.Shields, 2));

        try {
            warehouse.addResource(new NumberOfResources(1,5,0,5));
            fail();
        } catch (UnableToFillException e) {}

        try {
            warehouse.addResource(new NumberOfResources(1,4,0,5));
        } catch (UnableToFillException e) {
            fail();
        }

    }

    @Test
    public  void subResourceTest(){
        WareHouseDepots warehouse= new WareHouseDepots();
        try {
            warehouse.addResource(new NumberOfResources(1,2,3,0));
        } catch (UnableToFillException e) {
            fail();
        }

        try {
            warehouse.subResource(new NumberOfResources(0,0,0,1));
            fail();
        } catch (OutOfResourcesException e) {
        }
        assertEquals(new NumberOfResources(1,2,3,0),warehouse.getResources());

        try {
            warehouse.subResource(new NumberOfResources(2,0,0,0));
            fail();
        } catch (OutOfResourcesException e) {
        }
        assertEquals(new NumberOfResources(1,2,3,0),warehouse.getResources());

        try {
            warehouse.subResource(new NumberOfResources(1,0,2,0));
        } catch (OutOfResourcesException e) {
            fail();
        }
        assertEquals(new NumberOfResources(0,2,1,0),warehouse.getResources());

        int i=0;
        for (Shelf x: warehouse.getShelfs()){
            if(i<1){
                assertNull(x.getResType());
                assertEquals(0,x.getUsed());
            }
            if(i==1){
                assertEquals(ResourceType.Coins,x.getResType());
                assertEquals(1,x.getUsed());
            }
            if (i>1){
                assertEquals(ResourceType.Shields,x.getResType());
                assertEquals(2,x.getUsed());
            }
            i++;
        }

        try {
            warehouse.subResource(new NumberOfResources(0,2,0,0));
        } catch (OutOfResourcesException e) {
            fail();
        }
        assertEquals(new NumberOfResources(0,0,1,0),warehouse.getResources());
        i=0;
        for (Shelf x: warehouse.getShelfs()){
            if(i<2){
                assertNull(x.getResType());
                assertEquals(0,x.getUsed());
            }
            if(i>1){
                assertEquals(ResourceType.Coins,x.getResType());
                assertEquals(1,x.getUsed());
            }
            i++;
        }

        try {
            warehouse.subResource(new NumberOfResources(0,0,1,0));
        } catch (OutOfResourcesException e) {
            fail();
        }
        assertEquals(new NumberOfResources(),warehouse.getResources());

        i=0;
        for (Shelf x: warehouse.getShelfs()){
            i++;
            assertEquals(0,x.getUsed());
            assertNull(x.getResType());
            assertEquals(i,x.getMaxSize());
        }

    }


}
