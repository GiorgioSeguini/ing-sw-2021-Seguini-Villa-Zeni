package it.polimi.ingsw.server.parse;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.DepotsExt;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class ChangeTest{

    @Test
    public void test(){
        DepotsExt depots = new DepotsExt(0);

        System.out.println(Starter.toJson(depots, DepotsExt.class));
        
        depots.addResourceFromProduction(new NumberOfResources(1,2,3,4));
        try {
            depots.addResourcesFromMarket(new NumberOfResources(1, 0,0, 0));
        } catch (UnableToFillException e) {
            fail();
        }

        System.out.println(Starter.toJson(depots, DepotsExt.class));


    }
}
