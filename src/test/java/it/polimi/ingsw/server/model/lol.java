package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.server.parse.NumberOfResSerializer;
import it.polimi.ingsw.server.parse.TokensSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class lol {

    @Test
    public void test(){
        Move2 move2= new Move2();
        Discard2 discard2= new Discard2(ColorDevCard.BLUE);
        MoveShuffle moveShuffle =new MoveShuffle();
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        Gson gson=builder.create();

        String tosend = gson.toJson(move2, SoloActionTokens.class)
                +gson.toJson(discard2, SoloActionTokens.class)
                +gson.toJson(moveShuffle, SoloActionTokens.class);

        System.out.println(tosend);
    }// TODO: 5/5/21 non capisco delle cose


    @Test
    public void test2(){
        NumberOfResources n = new NumberOfResources(0, 1, 3,5);
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson=builder.create();

        String out = gson.toJson(n);
        System.out.println(out);
    }


    @Test
    public void test3() throws FileNotFoundException {
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        Gson gson=builder.create();

        Type TokensListType = new TypeToken<ArrayList<SoloActionTokens>>(){}.getType();
        String filePath = new File("").getAbsolutePath();
        ArrayList<SoloActionTokens> tokens = gson.fromJson(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"), TokensListType);

        for(SoloActionTokens token : tokens){
            System.out.println(gson.toJson(token, SoloActionTokens.class));
        }

    }
}
