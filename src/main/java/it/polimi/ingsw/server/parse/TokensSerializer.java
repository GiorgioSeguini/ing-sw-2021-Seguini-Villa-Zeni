package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.server.model.Discard2;
import it.polimi.ingsw.server.model.Move2;
import it.polimi.ingsw.server.model.MoveShuffle;
import it.polimi.ingsw.server.model.SoloActionTokens;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class TokensSerializer implements JsonSerializer<SoloActionTokens>, JsonDeserializer<SoloActionTokens> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE  = "INSTANCE";

    private static Map<String, Class> map = new TreeMap<String, Class>();

    static {
        map.put(Discard2.name, Discard2.class);
        map.put(Move2.name, Move2.class);
        map.put(MoveShuffle.name, MoveShuffle.class);
    }



    @Override
    public SoloActionTokens deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = map.get(className);
        if (c == null)
            throw new RuntimeException("Unknow class: " + type);

        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

    @Override
    public JsonElement serialize(SoloActionTokens soloActionTokens, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, soloActionTokens.getName());
        JsonElement elem = context.serialize(soloActionTokens);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
