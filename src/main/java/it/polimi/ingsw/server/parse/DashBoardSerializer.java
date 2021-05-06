package it.polimi.ingsw.server.parse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.DevelopmentCard;

import java.lang.reflect.Type;

public class DashBoardSerializer implements JsonSerializer<Dashboard> {

    @Override
    public JsonElement serialize(Dashboard dashboard, Type type, JsonSerializationContext context) {

        JsonObject retValue = new JsonObject();
        DevelopmentCard[][] cards = new DevelopmentCard[Level.size()][ColorDevCard.size()];

        for(ColorDevCard c: ColorDevCard.values()){
            for(Level l : Level.values()){
                cards[l.ordinal()][c.ordinal()]=dashboard.getTopDevCard(c, l);
            }
        }
        retValue.add("dashBoard", context.serialize(cards));
        return retValue;
    }
}
