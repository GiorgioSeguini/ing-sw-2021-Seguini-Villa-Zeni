package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.server.model.ProductionPowerExt;

import java.lang.reflect.Type;

public class ProductionPowerExtSerializer implements JsonDeserializer<ProductionPower> {

    @Override
    public ProductionPower deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, ProductionPowerExt.class);
    }
}
