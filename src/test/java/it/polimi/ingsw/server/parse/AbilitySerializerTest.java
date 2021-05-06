package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.Ability;
import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.NumberOfResources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AbilitySerializerTest {


    @Test
    public void testx() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        try {
            leaderCards = Starter.LeaderCardsParser();
        } catch (IOException e) {
            fail();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        gsonBuilder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson = gsonBuilder.create();

        Type LeaderListType = new TypeToken<ArrayList<LeaderCard>>() {}.getType();
        String message = gson.toJson(leaderCards, LeaderListType);

        ArrayList<LeaderCard> leaderCards1 = gson.fromJson(message, LeaderListType);
        int i=0;
        for (LeaderCard card: leaderCards){
            assertEquals(card,leaderCards1.get(i));
            i++;
        }
    }


}
