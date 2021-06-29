package it.polimi.ingsw.constant.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.setupper.*;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * SetUpper Serializer
 * Implements Gson interface for serialization and deserialization of SetUppers classes
 * Serialization and deserialization are used both on client and server side
 */
public class SetupperSerializer implements JsonSerializer<SetUp> , JsonDeserializer<SetUp> {

    private static final HashMap<String, Class> mapNameClass= new HashMap<>();
    private static final String CLASSNAME= "CLASSNAME";
    private static final String INSTANCE= "INSTANCE";

    static{
        mapNameClass.put(CreateRoomSetupper.className, CreateRoomSetupper.class);
        mapNameClass.put(JoinWaitngListSetupper.className, JoinWaitngListSetupper.class);
        mapNameClass.put(LinkToRoomSetupper.className,LinkToRoomSetupper.class);
        mapNameClass.put(DisconnectConnectionSetupper.className, DisconnectConnectionSetupper.class);
    }

    @Override
    public SetUp deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = mapNameClass.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

    @Override
    public JsonElement serialize(SetUp setUp, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, setUp.getClassName());
        JsonElement elem = context.serialize(setUp);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
