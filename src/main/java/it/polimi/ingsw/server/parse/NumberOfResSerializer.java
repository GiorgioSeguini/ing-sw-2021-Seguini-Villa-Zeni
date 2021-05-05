package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.NumberOfResources;

import java.lang.reflect.Type;

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
