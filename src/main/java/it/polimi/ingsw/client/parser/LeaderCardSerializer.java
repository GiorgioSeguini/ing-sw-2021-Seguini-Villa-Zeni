package it.polimi.ingsw.client.parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.model.LeaderCard;

import java.lang.reflect.Type;

public class LeaderCardSerializer implements JsonDeserializer<LeaderCard> {
    @Override
    public LeaderCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, LeaderCardClient.class);
    }
}
