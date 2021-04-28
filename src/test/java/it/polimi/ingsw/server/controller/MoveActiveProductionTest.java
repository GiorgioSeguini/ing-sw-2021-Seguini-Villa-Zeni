package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.ProductionPower;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveActiveProductionTest {

    @Test
    public void ConstructorTest(){
        Player player = new Player("Fabio");
        ProductionPower productionPowerbase = new ProductionPower();
        ProductionPower productionPower = new ProductionPower(new NumberOfResources(0,0,0,1), new NumberOfResources(0,2,0,0));
        ProductionPower[] toActive = new ProductionPower[] {productionPowerbase,productionPower};
        MoveActiveProduction moveActiveProduction = new MoveActiveProduction(player,toActive);
        assertNotNull(moveActiveProduction.player);
        for(ProductionPower x : moveActiveProduction.toActive) {
            assertNotNull(x);
        }
        assertNotNull(moveActiveProduction.allowedStatus);
    }

    @Test
    public void CanPerformTest(){

    }
}
