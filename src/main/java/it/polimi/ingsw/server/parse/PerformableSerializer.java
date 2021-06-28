package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.server.controller.*;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * PerformableSerializer class.
 * Implements JsonDeserializer<Performable> interface.
 */
public class PerformableSerializer implements JsonDeserializer<Performable>{

    private static final HashMap<String,Class> moveNames= new HashMap<>();
    private static final String CLASSNAME="CLASSNAME";
    private static final String INSTANCE="INSTANCE";


    static {
        moveNames.put(MoveActiveProductionExt.className, MoveActiveProductionExt.class);
        moveNames.put(MoveBuyDevCardExt.className, MoveBuyDevCardExt.class);
        moveNames.put(MoveChoseInitialResourcesExt.className, MoveChoseInitialResourcesExt.class);
        moveNames.put(MoveChoseResourcesExt.className, MoveChoseResourcesExt.class);
        moveNames.put(MoveChoseLeaderCardsExt.className, MoveChoseLeaderCardsExt.class);
        moveNames.put(MoveDiscardResourcesExt.className, MoveDiscardResourcesExt.class);
        moveNames.put(MoveEndTurnExt.className, MoveEndTurnExt.class);
        moveNames.put(MoveLeaderExt.className, MoveLeaderExt.class);
        moveNames.put(MoveTypeMarketExt.className, MoveTypeMarketExt.class);
        moveNames.put(MoveWhiteConversionExt.className, MoveWhiteConversionExt.class);
    }

    /**
     * Method that deserialize a JsonElement.
     * @param json of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param context of type JsonDeserializationContext
     * @return of type Performable: the performable deserialized.
     * @throws JsonParseException
     */
    @Override
    public Performable deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = moveNames.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }
}
