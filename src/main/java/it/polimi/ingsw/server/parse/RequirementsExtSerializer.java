package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.constant.model.Requirements;
import it.polimi.ingsw.server.model.RequirementsExt;

import java.lang.reflect.Type;

public class RequirementsExtSerializer implements JsonDeserializer<Requirements> {

    @Override
    public Requirements deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, RequirementsExt.class);
    }
}
