package it.polimi.ingsw.server.parse;

import it.polimi.ingsw.server.controller.MoveActiveProduction;
import it.polimi.ingsw.server.controller.MoveEndTurn;
import it.polimi.ingsw.server.controller.MoveType;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.ProductionPower;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MoveTypeSerializerTest {

    @Test
    public void test(){
        ArrayList<ProductionPower> productionPowers= new ArrayList<>();
        productionPowers.add(new ProductionPower());
        productionPowers.add(new ProductionPower(2, new NumberOfResources(0,1,2,3), new NumberOfResources(3,2,4,0)));
        productionPowers.add(new ProductionPower(1, new NumberOfResources(1,0,2,3), new NumberOfResources(3,0,4,0), 3,1));

        MoveType move= new MoveActiveProduction(new Player("lol").getID(), productionPowers);
        String message=Starter.toJson(move);

        System.out.println(message);
        // TODO: 5/6/21 change
    }

    @Test
    public void test2(){
        MoveType move= new MoveEndTurn(new Player("ciao").getID());
        System.out.println(Starter.toJson(move));
    } // TODO: 5/6/21 change

}
