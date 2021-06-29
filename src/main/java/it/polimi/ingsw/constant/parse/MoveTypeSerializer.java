package it.polimi.ingsw.constant.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.move.*;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Move Type serializer.
 * Implements Gson interface for serialization and deserialization of MoveType class
 * Contains an hashmap of MoveType classes
 * Used to serialize MoveType on client side
 */
public class MoveTypeSerializer implements JsonSerializer<MoveType>, JsonDeserializer<MoveType> {

    private static final HashMap<String,Class> moveNames= new HashMap<>();
    private static final String CLASSNAME="CLASSNAME";
    private static final String INSTANCE="INSTANCE";


    static {
        moveNames.put(MoveActiveProduction.className, MoveActiveProduction.class);
        moveNames.put(MoveBuyDevCard.className, MoveBuyDevCard.class);
        moveNames.put(MoveChoseInitialResources.className, MoveChoseInitialResources.class);
        moveNames.put(MoveChoseResources.className, MoveChoseResources.class);
        moveNames.put(MoveChoseLeaderCards.className, MoveChoseLeaderCards.class);
        moveNames.put(MoveDiscardResources.className, MoveDiscardResources.class);
        moveNames.put(MoveEndTurn.className, MoveEndTurn.class);
        moveNames.put(MoveLeader.className, MoveLeader.class);
        moveNames.put(MoveTypeMarket.className, MoveTypeMarket.class);
        moveNames.put(MoveWhiteConversion.className, MoveWhiteConversion.class);
    }

    @Override
    public MoveType deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = moveNames.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

    @Override
    public JsonElement serialize(MoveType moveType, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, moveType.getClassName());
        Class c= moveNames.get(moveType.getClassName());
        JsonElement elem = context.serialize(moveType, c);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
