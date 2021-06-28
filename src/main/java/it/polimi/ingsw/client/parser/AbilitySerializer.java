package it.polimi.ingsw.client.parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.client.modelClient.Ability;
import it.polimi.ingsw.client.modelClient.AbilityType;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.lang.reflect.Type;

public class AbilitySerializer implements JsonDeserializer<Ability> {
    private static final String CLASSNAME="CLASSNAME";
    private static final String INSTANCE="INSTANCE";

    @Override
    public Ability deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        String className = json.getAsJsonObject().get(CLASSNAME).getAsString();
        AbilityType abilityType = AbilityType.valueOf(className);
        ResourceType resourceType = ResourceType.valueOf(json.getAsJsonObject().get(INSTANCE).getAsJsonObject().get("typeOfRes").getAsString());
        return new Ability(resourceType, abilityType);
    }

}
