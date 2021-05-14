package it.polimi.ingsw.client.parser;

import com.google.gson.*;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;

import java.lang.reflect.Type;

public class NumberOfResSerializer implements JsonSerializer<NumberOfResources>, JsonDeserializer<NumberOfResources> {


    @Override
    public JsonElement serialize(NumberOfResources src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject retValue = new JsonObject();
        for(ResourceType type : ResourceType.values()){
            retValue.addProperty(type.name(), src.getAmountOf(type));
        }
        return retValue;
    }

    @Override
    public NumberOfResources deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException  {
        JsonObject jsonObject = json.getAsJsonObject();

        NumberOfResources result = new NumberOfResources();
        for(ResourceType type : ResourceType.values()){
            result = result.add(type, jsonObject.get(type.name()).getAsInt());
        }
        return result;
    }
}
