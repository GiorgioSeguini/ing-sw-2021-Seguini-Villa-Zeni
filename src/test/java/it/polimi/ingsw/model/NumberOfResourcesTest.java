package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ResourceType;
import it.polimi.ingsw.model.exception.OutOfResourcesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberOfResourcesTest {

    @Test
    void getAmountOf() {
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
    }

    @Test
    void max_Resource_Type() {
    }

    @Test
    void testClone() {
    }

    @Test
    void size() {
    }

    @Test
    void isEmpty() {
        assertTrue(new NumberOfResources().isEmpty());


    }
}