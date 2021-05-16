package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.server.model.DevelopmentCardExt;

import java.lang.reflect.Type;

public class DevCardExtSerializer implements JsonDeserializer<DevelopmentCard>{

    @Override
    public DevelopmentCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(jsonElement, DevelopmentCardExt.class);
    }

}
