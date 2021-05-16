package it.polimi.ingsw.client.parser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.client.modelClient.DashBoardClient;
import it.polimi.ingsw.constant.model.Dashboard;

import java.lang.reflect.Type;

public class DashBoardClientDeserializer implements JsonDeserializer<Dashboard> {

    @Override
    public Dashboard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, DashBoardClient.class);
    }
}
