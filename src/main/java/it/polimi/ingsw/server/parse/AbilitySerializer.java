package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.server.model.*;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * AbilitySerializer class.
 * Implements JsonSerializer<Ability> and JsonDeserializer<Ability> interfaces.
 */
public class AbilitySerializer implements JsonSerializer<Ability>, JsonDeserializer<Ability> {
    private static final HashMap<String, Class> mapNameClass= new HashMap<>();
    private static final String CLASSNAME="CLASSNAME";
    private static final String INSTANCE="INSTANCE";

    static {
        mapNameClass.put(WhiteAbility.name, WhiteAbility.class);
        mapNameClass.put(DepotsAbility.name, DepotsAbility.class);
        mapNameClass.put(ProductionPowerPlusAbility.name, ProductionPowerPlusAbility.class);
        mapNameClass.put(DiscountAbility.name, DiscountAbility.class);
    }

    /**
     * Method that deserialize a JsonElement.
     * @param json of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param context of type JsonDeserializationContext
     * @return of type Ability:  the ability deserialized.
     * @throws JsonParseException
     */
    @Override
    public Ability deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = mapNameClass.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

    /**
     * Method that serialize an Ability
     * @param ability of type Ability: the ability that has to be serialized.
     * @param type of type Type
     * @param context of type JsonDeserializationContext
     * @return of type JsonElement: the Json serialized.
     */
    @Override
    public JsonElement serialize(Ability ability, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, ability.getName());
        JsonElement elem = context.serialize(ability);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
