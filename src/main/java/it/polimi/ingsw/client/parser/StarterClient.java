package it.polimi.ingsw.client.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.modelClient.Ability;
import it.polimi.ingsw.constant.model.Dashboard;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.MessageSerializer;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.parse.MoveTypeSerializer;
import it.polimi.ingsw.constant.parse.NumberOfResSerializer;


import java.lang.reflect.Type;

public class StarterClient{
    private static final Gson gson;

    static{
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(Message.class, new MessageSerializer());
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        builder.registerTypeAdapter(MoveType.class, new MoveTypeSerializer());
        builder.registerTypeAdapter(Dashboard.class, new DashBoardClientDeserializer());
        builder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardSerializer());
        gson=builder.create();
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static  <T> T fromJson(String json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }

    public static String toJson(Object src, Type typeOfSrc){
        return gson.toJson(src, typeOfSrc);
    }
}
