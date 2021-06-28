package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.server.model.Discard2;
import it.polimi.ingsw.server.model.Move2;
import it.polimi.ingsw.server.model.MoveShuffle;
import it.polimi.ingsw.server.model.SoloActionTokens;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

/**
 * TokensSerializer class.
 * Implements JsonSerializer<SoloActionTokens> and JsonDeserializer<SoloActionTokens> interfaces.
 */
public class TokensSerializer implements JsonSerializer<SoloActionTokens>, JsonDeserializer<SoloActionTokens> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE  = "INSTANCE";

    private static final Map<String, Class> map = new TreeMap<String, Class>();

    static {
        map.put(Discard2.name, Discard2.class);
        map.put(Move2.name, Move2.class);
        map.put(MoveShuffle.name, MoveShuffle.class);
    }

    /**
     * Method that deserialize a JsonElement.
     * @param json of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param context of type JsonDeserializationContext
     * @return of type SoloActionTokens: the SoloActionTokens deserialized.
     * @throws JsonParseException
     */
    @Override
    public SoloActionTokens deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = map.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

    /**
     * Method that serialize a LeaderCard.
     * @param soloActionTokens of type SoloActionTokens: the SoloActionTokens that has to be serialized.
     * @param type of type Type
     * @param context of type JsonSerializationContext
     * @return of type JsonElement:  the Json serialized.
     */
    @Override
    public JsonElement serialize(SoloActionTokens soloActionTokens, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, soloActionTokens.getName());
        JsonElement elem = context.serialize(soloActionTokens);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
