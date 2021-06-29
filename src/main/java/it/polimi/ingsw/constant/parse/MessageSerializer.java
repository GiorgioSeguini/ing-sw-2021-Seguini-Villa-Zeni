package it.polimi.ingsw.constant.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.message.*;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * The type Message serializer, contains an hashmap of the message that allows to serialize (on server side) and deserialize ( on client side) Message classes correctly
 */
public class MessageSerializer implements JsonSerializer<Message>, JsonDeserializer<Message> {

    private static final HashMap<String, Class> mapNameClass= new HashMap<>();
    private static final String CLASSNAME="CLASSNAME";
    private static final String INSTANCE="INSTANCE";

    static {
        mapNameClass.put(InitialMessage.className, InitialMessage.class);
        mapNameClass.put(DashBoardMessage.className, DashBoardMessage.class);
        mapNameClass.put(FaithTrackMessage.className, FaithTrackMessage.class);
        mapNameClass.put(GameMessage.className, GameMessage.class);
        mapNameClass.put(MarketMessage.className, MarketMessage.class);
        mapNameClass.put(PersonalBoardMessage.className, PersonalBoardMessage.class);
        mapNameClass.put(DepotsMessage.className, DepotsMessage.class);
        mapNameClass.put(PlayerMessage.className, PlayerMessage.class);
        mapNameClass.put(ConverterMessage.className, ConverterMessage.class);
        mapNameClass.put(LastMessage.className, LastMessage.class);
        mapNameClass.put(RejectMessage.className, RejectMessage.class);
        mapNameClass.put(AcceptMessage.className, AcceptMessage.class);
        mapNameClass.put(ErrorMessage.className, ErrorMessage.class);
        mapNameClass.put(ConnectionMessage.className, ConnectionMessage.class);
        mapNameClass.put(ReconnectMessage.className, ReconnectMessage.class);
        mapNameClass.put(DisconnectMessage.className,DisconnectMessage.class);
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
