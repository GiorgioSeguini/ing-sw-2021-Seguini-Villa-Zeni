package it.polimi.ingsw.server.parse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.server.model.ProductionPowerExt;

import java.lang.reflect.Type;

/**
 * ProductionPowerExtSerializer class.
 * Implements JsonDeserializer<ProductionPower> interface.
 */
public class ProductionPowerExtSerializer implements JsonDeserializer<ProductionPower> {

    /**
     * Method that deserialize a JsonElement.
     * @param jsonElement of type JsonElement: the JsonElement that has to be deserialized.
     * @param type of type Type
     * @param jsonDeserializationContext of type JsonDeserializationContext
     * @return of type ProductionPower: the production power deserialized.
     * @throws JsonParseException
     */
    @Override
    public ProductionPower deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonDeserializationContext.deserialize(jsonElement, ProductionPowerExt.class);
    }
}
