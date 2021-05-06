package it.polimi.ingsw.client.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.modelClient.NumberOfResources;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.MessageSerializer;


import java.io.File;

public class StarterClient {
    private static final Gson gson;
    private static final String filePath;

    static{
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(Message.class, new MessageSerializer());
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        gson=builder.create();
        filePath = new File("").getAbsolutePath();
    }

    public static Message fromJsonMessage(String json){
        return gson.fromJson(json, Message.class);
    }
}
