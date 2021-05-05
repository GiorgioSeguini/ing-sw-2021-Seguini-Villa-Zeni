package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.server.model.NumberOfResources;
import org.junit.jupiter.api.Test;

public class NumOfResSerializerTest {

    @Test
    public void test2(){
        NumberOfResources n = new NumberOfResources(0, 1, 3,5);
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson=builder.create();

        String out = gson.toJson(n);
        System.out.println(out);
    }// TODO: 5/5/21 da sistemare

}
