package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.constant.model.Requirements;
import it.polimi.ingsw.server.model.RequirementsExt;

import java.lang.reflect.Type;

/**
 * RequirementsExtSerializer class.
 * Implements JsonDeserializer<Requirements> interface.
 */
public class RequirementsExtSerializer implements JsonDeserializer<Requirements> {

    /**
     * Method that deserialize a JsonElement.
     * @param jsonElement of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param jsonDeserializationContext of type JsonDeserializationContext
     * @return of type Requirements: the requirement deserialized.
     * @throws JsonParseException
     */
    @Override
    public Requirements deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, RequirementsExt.class);
    }
}
