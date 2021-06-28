package it.polimi.ingsw.server.parse;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.server.controller.MoveActiveProductionExt;
import it.polimi.ingsw.server.controller.MoveBuyDevCardExt;
import it.polimi.ingsw.server.controller.MoveEndTurnExt;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MoveTypeSerializerTest {

    @Test
    public void test(){
        ArrayList<ProductionPowerExt> productionPowers= new ArrayList<>();
        productionPowers.add(new ProductionPowerExt());
        productionPowers.add(new ProductionPowerExt(2, new NumberOfResources(0,1,2,3), new NumberOfResources(3,2,4,0)));
        productionPowers.add(new ProductionPowerExt(1, new NumberOfResources(1,0,2,3), new NumberOfResources(3,0,4,0), 3,1));

        MoveType move= new MoveActiveProductionExt(new PlayerExt("lol").getID(), productionPowers);
        String message=Starter.toJson(move, MoveType.class);

        System.out.println(message);
    }

    @Test
    public void test2(){
        MoveType move= new MoveEndTurnExt(new PlayerExt("ciao").getID());
        System.out.println(Starter.toJson(move, MoveType.class));
    }

    @Test
    public void test3(){
        MoveType move= new MoveEndTurnExt(new PlayerExt("pioo").getID());
        String message=Starter.toJson(move, MoveType.class);
        MoveType move2= (MoveType) Starter.fromJson(message, MoveType.class);

        assertEquals(move, move2);

    }

    @Test
    public void test4() {
        ArrayList<DevelopmentCardExt> developmentCards= new ArrayList<>();

        developmentCards=Starter.DevCardParser();

        ArrayList<ProductionPowerExt> productionPowers= new ArrayList<>();
        for (DevelopmentCardExt card: developmentCards){
            productionPowers.add(card.getProductionPower());
        }

        MoveType move= new MoveActiveProductionExt(new PlayerExt("pippo").getID(), productionPowers);
        String message=Starter.toJson(move, MoveType.class);

        MoveType move2= (MoveType) Starter.fromJson(message, MoveType.class);

        assertEquals(move, move2);

    }


    @Test
    public void test5(){
        ArrayList<DevelopmentCardExt> developmentCards= new ArrayList<>();

        developmentCards=Starter.DevCardParser();

        DevelopmentCardExt card= developmentCards.get(8);
        MoveType move= new MoveBuyDevCardExt(new PlayerExt("lol").getID(), 7, card.getId());

        String message= Starter.toJson(move, MoveType.class);
        MoveType move2= (MoveType) Starter.fromJson(message, MoveType.class);

        assertEquals(move, move2);
    }

}
