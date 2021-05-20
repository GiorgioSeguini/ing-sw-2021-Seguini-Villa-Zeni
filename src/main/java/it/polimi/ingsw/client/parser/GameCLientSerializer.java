package it.polimi.ingsw.client.parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;

import java.lang.reflect.Type;

public class GameCLientSerializer implements JsonDeserializer<Game> {

    @Override
    public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, GameClient.class);
    }
}
