package it.polimi.ingsw.client.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.modelClient.*;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.MessageSerializer;


import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StarterClient{
    private static final Gson gson;

    static{
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(Message.class, new MessageSerializer());
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        gson=builder.create();
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static  <T> T fromJson(String json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }
}
