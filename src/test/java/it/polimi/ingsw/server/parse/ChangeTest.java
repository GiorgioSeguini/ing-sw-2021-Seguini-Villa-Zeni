package it.polimi.ingsw.server.parse;

import it.polimi.ingsw.server.model.Depots;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class ChangeTest{

    @Test
    public void test(){
        Depots depots = new Depots(0);

        System.out.println(Starter.toJson(depots, Depots.class));
        
        depots.addResourceFromProduction(new NumberOfResources(1,2,3,4));
        try {
            depots.addResourcesFromMarket(new NumberOfResources(1, 0,0, 0));
        } catch (UnableToFillException e) {
            fail();
        }

        System.out.println(Starter.toJson(depots, Depots.class));


    }
}
