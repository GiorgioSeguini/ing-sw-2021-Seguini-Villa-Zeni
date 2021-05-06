package it.polimi.ingsw.client.parser;

import com.google.gson.*;
import it.polimi.ingsw.client.modelClient.Ability;
import it.polimi.ingsw.client.modelClient.AbilityType;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.lang.reflect.Type;
import java.util.HashMap;

public class AbilitySerializer implements JsonSerializer<Ability>, JsonDeserializer<Ability> {
    private static HashMap<String, AbilityType> mapNameClass= new HashMap<>();
    private static final String CLASSNAME="CLASSNAME";
    private static final String INSTANCE="INSTANCE";

    @Override
    public Ability deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        AbilityType abilityType = AbilityType.valueOf(className);
        ResourceType resourceType = ResourceType.valueOf(json.getAsJsonObject().get(INSTANCE).getAsJsonObject().get("typeOfRes").getAsString());
        return new Ability(resourceType, abilityType);
    }

    @Override
    public JsonElement serialize(Ability ability, Type type, JsonSerializationContext context) {
        //TODO
        //should be useless
        return null;
    }
}
