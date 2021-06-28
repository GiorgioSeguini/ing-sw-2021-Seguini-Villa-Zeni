package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.server.model.LeaderCardExt;

import java.lang.reflect.Type;

/**
 * LeaderCardExtSerializer class.
 * Implements JsonDeserializer<LeaderCard> and JsonSerializer<LeaderCard> interfaces.
 */
public class LeaderCardExtSerializer implements JsonDeserializer<LeaderCard>, JsonSerializer<LeaderCard> {

    /**
     * Method that deserialize a JsonElement.
     * @param jsonElement of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param context of type JsonDeserializationContext
     * @return of type LeaderCard: the leader card deserialized.
     * @throws JsonParseException
     */
    @Override
    public LeaderCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(jsonElement, LeaderCardExt.class);
    }

    /**
     * Method that serialize a LeaderCard.
     * @param leaderCard of type LeaderCard: the leader card that has to be serialized.
     * @param type of type Type
     * @param jsonSerializationContext of type JsonSerializationContext
     * @return of type JsonElement:  the Json serialized.
     */
    @Override
    public JsonElement serialize(LeaderCard leaderCard, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(leaderCard, LeaderCardExt.class);
    }
}
