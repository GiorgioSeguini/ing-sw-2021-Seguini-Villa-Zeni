package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.server.model.SoloActionTokens;

import java.lang.reflect.Type;

public class TokensSerializer implements JsonSerializer<SoloActionTokens>, JsonDeserializer<SoloActionTokens> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE  = "INSTANCE";

    @Override
    public SoloActionTokens deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        Class<?> klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), klass);
    }

    @Override
    public JsonElement serialize(SoloActionTokens soloActionTokens, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        String className = soloActionTokens.getClass().getName();
        retValue.addProperty(CLASSNAME, className);
        JsonElement elem = context.serialize(soloActionTokens);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
