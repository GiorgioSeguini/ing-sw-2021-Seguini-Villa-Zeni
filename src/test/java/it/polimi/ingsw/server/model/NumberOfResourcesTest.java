package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberOfResourcesTest {

    @Test
    void getAmountOf() {
        try {
            NumberOfResources test = new NumberOfResources(0, -1, 2, 3);
        }catch(IllegalArgumentException e){}
        NumberOfResources test=new NumberOfResources(0,1,2,3);
        NumberOfResources test2= new NumberOfResources();
        int i=0;
        for (ResourceType x: ResourceType.values()){
            assertEquals(test.getAmountOf(x),i);
            i++;
        }
        for (ResourceType x: ResourceType.values()){
            assertEquals(test2.getAmountOf(x),0);
        }
    }

    @Test
    void testAdd() {
        NumberOfResources test= new NumberOfResources();
        NumberOfResources test2= new NumberOfResources(1,2,5,2);
        NumberOfResources test3= new NumberOfResources(0,3,1,2);

        test=test.add(test2);
        assertEquals(test,test2);
        test=test.add(test3);
        assertEquals(test,new NumberOfResources(1,5,6,4));
        test=test.add(ResourceType.Coins,4);
        assertEquals(test,new NumberOfResources(1,5,10,4));

    }

    @Test
    void testSub() {
        NumberOfResources test= new NumberOfResources();
        NumberOfResources test2= new NumberOfResources(1,1,1,1);

        try{
            test=test2.sub(test);
        }catch (OutOfResourcesException e){
            fail();
        }
        assertEquals(test,new NumberOfResources(1,1,1,1));

        try{
            test=test2.sub(test);
        }catch (OutOfResourcesException e){
            fail();
        }
        assertEquals(test,new NumberOfResources());

        try{
            test=test.sub(new NumberOfResources(1000,10000,10000,10000));
            fail();
        }catch (OutOfResourcesException e){
        }
        assertEquals(test,new NumberOfResources());

        try{
            test=test.sub(ResourceType.Shields,3);
            fail();
        }catch (OutOfResourcesException e){
        }
        assertEquals(test,new NumberOfResources());

        test=test.add(ResourceType.Shields,3);

        try{
            test=test.sub(ResourceType.Shields,2);
        }catch (OutOfResourcesException e){
            fail();
        }
        assertEquals(test,new NumberOfResources(0,1,0,0));

        test2= new NumberOfResources(1, 1, 1, 1);
        NumberOfResources test3= new NumberOfResources(1, 0, 0, 1);
        try {
            test2=test2.sub(test3);
        } catch (OutOfResourcesException e) {
            fail();
        }

        assertEquals(new NumberOfResources(0,1,1,0), test2);
    }

    @Test
    void max_Resource_Type(){
        NumberOfResources test=new NumberOfResources(5,0,4,3);
        NumberOfResources test1=new NumberOfResources(2,5,4,3);
        NumberOfResources test2=new NumberOfResources(2,0,5,3);
        NumberOfResources test3=new NumberOfResources(2,0,4,5);
        NumberOfResources test4=new NumberOfResources();
        NumberOfResources test5=new NumberOfResources(2,4,4,3);
        NumberOfResources test6=new NumberOfResources(2,3,4,4);

        ResourceType app= test.Max_Resource_Type();
        assertEquals(app, ResourceType.Servants);
        app= test1.Max_Resource_Type();
        assertEquals(app, ResourceType.Shields);
        app= test2.Max_Resource_Type();
        assertEquals(app, ResourceType.Coins);
        app= test3.Max_Resource_Type();
        assertEquals(app, ResourceType.Stones);
        app= test4.Max_Resource_Type();
        assertEquals(app, ResourceType.Servants);
        app= test5.Max_Resource_Type();
        assertEquals(app, ResourceType.Shields);
        app= test6.Max_Resource_Type();
        assertEquals(app, ResourceType.Coins);
    }

    @Test
    void testClone() {
        NumberOfResources test = new NumberOfResources(5, 4, 4, 3);
        NumberOfResources test1 = new NumberOfResources(2, 5, 4, 3);
        NumberOfResources test2 = new NumberOfResources(2, 0, 5, 3);
        NumberOfResources test3 = new NumberOfResources(2, 0, 4, 5);
        NumberOfResources test4 = new NumberOfResources();
        assertEquals(test, test.clone());
        assertEquals(test1, test1.clone());
        assertEquals(test2, test2.clone());
        assertEquals(test3, test3.clone());
        assertEquals(test4, test4.clone());

    }

    @Test
    void size() {
        NumberOfResources test = new NumberOfResources(0, 1, 0, 0);
        NumberOfResources test1 = new NumberOfResources(1, 1, 0, 0);
        NumberOfResources test2 = new NumberOfResources(2, 0, 0, 1);
        NumberOfResources test3 = new NumberOfResources(2, 0, 1, 5);
        NumberOfResources test4 = new NumberOfResources();

        assertEquals(test.size(),1);
        assertEquals(test1.size(),2);
        assertEquals(test2.size(),3);
        assertEquals(test3.size(),8);
        assertEquals(test4.size(),0);
    }

    @Test
    void isEmpty() {
        assertTrue(new NumberOfResources().isEmpty());
    }

    @Test
    void testEquals(){
        NumberOfResources test = new NumberOfResources(5, 4, 4, 3);
        NumberOfResources test1 = new NumberOfResources(2, 5, 4, 3);
        NumberOfResources test2 = new NumberOfResources(2, 0, 5, 3);
        NumberOfResources test3 = new NumberOfResources(5, 4, 4, 3);
        NumberOfResources test4 = new NumberOfResources();

        assertEquals(test, test);
        assertNotEquals(test, new Object());
        assertEquals(test, test3);
        assertNotEquals(test1, test);
        assertNotEquals(test, test4);
        assertNotEquals(test1, test4);
        assertEquals(test4, test4);

    }

    @Test
    void exception(){
        NumberOfResources test = new NumberOfResources();

        try {
            test.sub(ResourceType.Coins, -1);
            fail();
        }catch(OutOfResourcesException e){
            fail();
        }
        catch(IllegalArgumentException ignored){}

        try {
            test.add(ResourceType.Coins, -1);
            fail();
        }
        catch(IllegalArgumentException ignored){}


    }

    @Test
    void safe_sub(){

        NumberOfResources test = new NumberOfResources();
        NumberOfResources test1 = new NumberOfResources(1, 1, 1, 1);
        NumberOfResources test2 = new NumberOfResources(2, 0, 0, 1);

        assertEquals(new NumberOfResources(), test.safe_sub(test1));

        assertEquals(new NumberOfResources(0, 1, 1, 0), test1.safe_sub(test2));
    }
}