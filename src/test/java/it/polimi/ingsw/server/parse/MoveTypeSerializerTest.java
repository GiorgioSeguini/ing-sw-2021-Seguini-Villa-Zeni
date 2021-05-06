package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.controller.MoveActiveProduction;
import it.polimi.ingsw.server.controller.MoveEndTurn;
import it.polimi.ingsw.server.controller.MoveType;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.ProductionPower;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MoveTypeSerializerTest {

    @Test
    public void test(){
        ArrayList<ProductionPower> productionPowers= new ArrayList<>();
        productionPowers.add(new ProductionPower());
        productionPowers.add(new ProductionPower(2, new NumberOfResources(0,1,2,3), new NumberOfResources(3,2,4,0)));
        productionPowers.add(new ProductionPower(1, new NumberOfResources(1,0,2,3), new NumberOfResources(3,0,4,0), 3,1));

        MoveType move= new MoveActiveProduction(new Player("Pippo"), productionPowers);

        GsonBuilder gsonBuilder= new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MoveType.class, new MoveTypeSerializer());
        gsonBuilder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson= gsonBuilder.create();

       // Type array= new TypeToken<ArrayList<ProductionPower>>(){}.getType();
        System.out.println(move.getClassName());
        String message=gson.toJson(move);

        System.out.println(message);

    }

    @Test
    public void test2(){
        MoveType move= new MoveEndTurn(new Player("pippo"));

        GsonBuilder gsonBuilder= new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MoveType.class, new MoveTypeSerializer());
        //gsonBuilder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson= gsonBuilder.create();

        System.out.println(gson.toJson(move,  MoveType.class));
    }

}
