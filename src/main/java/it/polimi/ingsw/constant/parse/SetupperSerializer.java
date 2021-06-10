package it.polimi.ingsw.constant.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SetupperSerializer implements JsonSerializer<SetUp> , JsonDeserializer<SetUp> {

    private static HashMap<String, Class> mapNameClass= new HashMap<>();
    private static final String CLASSNAME= "CLASSNAME";
    private static final String INSTANCE= "INSTANCE";

    static{
        mapNameClass.put(CreateRoomSetupper.className, CreateRoomSetupper.class);
        mapNameClass.put(JoinWaitngListSetupper.className, JoinWaitngListSetupper.class);
        mapNameClass.put(LinkToRoomSetupper.className,LinkToRoomSetupper.class);
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
