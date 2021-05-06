package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.controller.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class MoveTypeSerializer implements JsonSerializer<MoveType>, JsonDeserializer<MoveType> {

    private static HashMap<String,Class> moveNames= new HashMap<>();
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
        moveNames.put(MovetypeMarket.className, MovetypeMarket.class);
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
