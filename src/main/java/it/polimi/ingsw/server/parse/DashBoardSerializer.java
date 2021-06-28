package it.polimi.ingsw.server.parse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.server.model.DashboardExt;

import java.lang.reflect.Type;

/**
 * DashBoardSerializer class.
 * Implements JsonSerializer<DashboardExt> interface.
 */
public class DashBoardSerializer implements JsonSerializer<DashboardExt> {

    /**
     * Method that serialize a DashboardExt
     * @param dashboard of type DashboardExt:  the dashboard that has to be serialized.
     * @param type of type Type
     * @param context of type JsonSerializationContext
     * @return of type JsonElement:  the Json serialized.
     */
    @Override
    public JsonElement serialize(DashboardExt dashboard, Type type, JsonSerializationContext context) {

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
