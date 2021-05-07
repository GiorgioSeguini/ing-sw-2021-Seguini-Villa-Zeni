package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.constant.MessageSerializer;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.controller.MoveActiveProduction;
import it.polimi.ingsw.server.controller.MoveBuyDevCard;
import it.polimi.ingsw.server.controller.MoveEndTurn;
import it.polimi.ingsw.server.controller.MoveType;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MoveTypeSerializerTest {

    @Test
    public void test(){
        ArrayList<ProductionPower> productionPowers= new ArrayList<>();
        productionPowers.add(new ProductionPower());
        productionPowers.add(new ProductionPower(2, new NumberOfResources(0,1,2,3), new NumberOfResources(3,2,4,0)));
        productionPowers.add(new ProductionPower(1, new NumberOfResources(1,0,2,3), new NumberOfResources(3,0,4,0), 3,1));

        MoveType move= new MoveActiveProduction(new Player("lol").getID(), productionPowers);
        String message=Starter.toJson(move, MoveType.class);

        System.out.println(message);
    }

    @Test
    public void test2(){
        MoveType move= new MoveEndTurn(new Player("ciao").getID());
        System.out.println(Starter.toJson(move, MoveType.class));
    }

    @Test
    public void test3(){
        MoveType move= new MoveEndTurn(new Player("pioo").getID());
        String message=Starter.toJson(move, MoveType.class);
        MoveType move2= (MoveType) Starter.fromJson(message, MoveType.class);

        assertEquals(move, move2);

    }

    @Test
    public void test4() {
        ArrayList<DevelopmentCard> developmentCards= new ArrayList<>();

        try {
            developmentCards=Starter.DevCardParser();
        } catch (FileNotFoundException e) {
            fail();
        }

        ArrayList<ProductionPower> productionPowers= new ArrayList<>();
        for (DevelopmentCard card: developmentCards){
            productionPowers.add(card.getProductionPower());
        }

        MoveType move= new MoveActiveProduction(new Player("pippo").getID(), productionPowers);
        String message=Starter.toJson(move, MoveType.class);

        MoveType move2= (MoveType) Starter.fromJson(message, MoveType.class);

        assertEquals(move, move2);

    }


    @Test
    public void test5(){
        ArrayList<DevelopmentCard> developmentCards= new ArrayList<>();

        try {
            developmentCards=Starter.DevCardParser();
        } catch (FileNotFoundException e) {
            fail();
        }

        DevelopmentCard card= developmentCards.get(8);
        MoveType move= new MoveBuyDevCard(new Player("lol").getID(), 7, card.getId());

        String message= Starter.toJson(move, MoveType.class);
        MoveType move2= (MoveType) Starter.fromJson(message, MoveType.class);

        assertEquals(move, move2);
    }

}
