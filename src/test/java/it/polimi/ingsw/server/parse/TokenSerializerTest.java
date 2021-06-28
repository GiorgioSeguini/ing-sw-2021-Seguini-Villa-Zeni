package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings("ALL")
public class TokenSerializerTest {

    @Test
    public void test(){
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        Gson gson=builder.create();

        Type TokensListType = new TypeToken<ArrayList<SoloActionTokens>>(){}.getType();
        String filePath = new File("").getAbsolutePath();
        ArrayList<SoloActionTokens> tokens = new ArrayList<>();
        try {
            tokens = gson.fromJson(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"), TokensListType);
        } catch (FileNotFoundException e) {
            fail();
        }

        String message= gson.toJson(tokens,TokensListType );

        ArrayList<SoloActionTokens> tokens1= gson.fromJson(message, TokensListType);

        int i=0;
        for (SoloActionTokens tok: tokens){
            assertEquals(tok, tokens1.get(i));
            i++;
        }


    }

    @Test
    public void test2(){
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();
        tokens=Starter.TokensParser();

        /*String message=Starter.toJson(tokens.get(3), SoloActionTokens.class);

        SoloActionTokens token= (SoloActionTokens) Starter.fromJson(message, SoloActionTokens.class);

        assertEquals(tokens.get(3), token);*/
    }


}
