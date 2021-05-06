package it.polimi.ingsw.constant;

import com.google.gson.*;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.server.model.DepotsAbility;
import it.polimi.ingsw.server.model.DiscountAbility;
import it.polimi.ingsw.server.model.ProductionPowerPlusAbility;
import it.polimi.ingsw.server.model.WhiteAbility;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MessageSerializer implements JsonSerializer<Message>, JsonDeserializer<Message> {

    private static HashMap<String, Class> mapNameClass= new HashMap<>();
    private static final String CLASSNAME="CLASSNAME";
    private static final String INSTANCE="INSTANCE";

    static {
        mapNameClass.put(WhiteAbility.name, WhiteAbility.class);
        mapNameClass.put(DepotsAbility.name, DepotsAbility.class);
        mapNameClass.put(ProductionPowerPlusAbility.name, ProductionPowerPlusAbility.class);
        mapNameClass.put(DiscountAbility.name, DiscountAbility.class);
    }

    @Override
    public Message deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = mapNameClass.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, message.getName());
        JsonElement elem = context.serialize(message);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
