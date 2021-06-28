package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.server.model.DevelopmentCardExt;

import java.lang.reflect.Type;

/**
 * DevCardExtSerializer class.
 * Implements JsonDeserializer<DevelopmentCard> interface.
 */
public class DevCardExtSerializer implements JsonDeserializer<DevelopmentCard>{

    /**
     * Method that deserialize a JsonElement.
     * @param jsonElement of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param context of type JsonDeserializationContext
     * @return of type DevelopmentCard: the development card deserialized.
     * @throws JsonParseException
     */
    @Override
    public DevelopmentCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(jsonElement, DevelopmentCardExt.class);
    }

}
