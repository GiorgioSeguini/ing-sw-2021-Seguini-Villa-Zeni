package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.server.model.LeaderCardExt;

import java.lang.reflect.Type;

public class LeaderCardExtSerializer implements JsonDeserializer<LeaderCard>, JsonSerializer<LeaderCard> {

    @Override
    public LeaderCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(jsonElement, LeaderCardExt.class);
    }

    @Override
    public JsonElement serialize(LeaderCard leaderCard, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(leaderCard, LeaderCardExt.class);
    }
}
