package it.polimi.ingsw.client.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import it.polimi.ingsw.client.modelClient.*;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.*;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.parse.MessageSerializer;
import it.polimi.ingsw.constant.parse.MoveTypeSerializer;
import it.polimi.ingsw.constant.parse.NumberOfResSerializer;
import it.polimi.ingsw.constant.parse.SetupperSerializer;
import it.polimi.ingsw.constant.setupper.SetUp;

import java.lang.reflect.Type;

public class StarterClient{
    private static final Gson gson;

    static{
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(Message.class, new MessageSerializer());
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        builder.registerTypeAdapter(MoveType.class, new MoveTypeSerializer());
        builder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        builder.registerTypeAdapter(SetUp.class, new SetupperSerializer());
        builder.registerTypeAdapter(Dashboard.class, (JsonDeserializer<Dashboard>) (json, type, context) -> context.deserialize(json, DashBoardClient.class));
        builder.registerTypeAdapter(LeaderCard.class,(JsonDeserializer<LeaderCard>) (json, type, context) -> context.deserialize(json, LeaderCardClient.class));
        builder.registerTypeAdapter(Game.class,(JsonDeserializer<Game>) (json, type, context) -> context.deserialize(json, GameClient.class));
        builder.registerTypeAdapter(LorenzoSoloPlayer.class, (JsonDeserializer<LorenzoSoloPlayer>) (json, type, context) -> context.deserialize(json, LorenzoSoloPlayerClient.class));
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
