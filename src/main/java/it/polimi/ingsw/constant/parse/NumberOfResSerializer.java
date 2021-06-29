package it.polimi.ingsw.constant.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;

import java.lang.reflect.Type;

/**
 * Number of resources Serializer
 * Implements Gson interface for serialization and deserialization of NumberOfResources class
 * Serialization and deserialization are used both on client and server side
 */
public class NumberOfResSerializer implements JsonSerializer<NumberOfResources>, JsonDeserializer<NumberOfResources> {


    @Override
    public JsonElement serialize(NumberOfResources src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject retValue = new JsonObject();
        retValue.addProperty("Servants", src.getAmountOf(ResourceType.Servants));
        retValue.addProperty("Shields", src.getAmountOf(ResourceType.Shields));
        retValue.addProperty("Coins", src.getAmountOf(ResourceType.Coins));
        retValue.addProperty("Stones", src.getAmountOf(ResourceType.Stones));
        return retValue;
    }

    @Override
    public NumberOfResources deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException  {
        JsonObject jsonObject = json.getAsJsonObject();

        return new NumberOfResources(
                jsonObject.get("Servants").getAsInt(),
                jsonObject.get("Shields").getAsInt(),
                jsonObject.get("Coins").getAsInt(),
                jsonObject.get("Stones").getAsInt());
    }
}
