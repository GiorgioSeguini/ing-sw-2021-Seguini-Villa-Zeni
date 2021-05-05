package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.server.model.Ability;
import it.polimi.ingsw.server.model.DepotsAbility;
import it.polimi.ingsw.server.model.DiscountAbility;
import it.polimi.ingsw.server.model.ProductionPowerPlusAbility;
import it.polimi.ingsw.server.model.WhiteAbility;

import java.lang.reflect.Type;
import java.util.HashMap;

public class AbilitySerializer implements JsonSerializer<Ability>, JsonDeserializer<Ability> {
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
    public Ability deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        Class c = mapNameClass.get(className);
        return context.deserialize(json.getAsJsonObject().get(INSTANCE), c);
    }

    @Override
    public JsonElement serialize(Ability ability, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, ability.getName());
        JsonElement elem = context.serialize(ability);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}
