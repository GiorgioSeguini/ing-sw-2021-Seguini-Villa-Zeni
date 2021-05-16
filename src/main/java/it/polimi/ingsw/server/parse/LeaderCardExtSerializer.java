package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.server.model.LeaderCardExt;

import java.lang.reflect.Type;

public class LeaderCardExtSerializer implements JsonDeserializer<LeaderCard> {

    @Override
    public LeaderCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(jsonElement, LeaderCardExt.class);
    }
}
