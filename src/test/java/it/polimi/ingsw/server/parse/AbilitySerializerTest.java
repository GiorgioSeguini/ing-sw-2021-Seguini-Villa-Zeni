package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.parse.NumberOfResSerializer;
import it.polimi.ingsw.server.model.Ability;
import it.polimi.ingsw.server.model.LeaderCardExt;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("ALL")
public class AbilitySerializerTest {


    @Test
    public void testx() {
        ArrayList<LeaderCardExt> leaderCards = new ArrayList<>();
        leaderCards = Starter.LeaderCardsParser();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        gsonBuilder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson = gsonBuilder.create();

        Type LeaderListType = new TypeToken<ArrayList<LeaderCardExt>>() {}.getType();
        String message = gson.toJson(leaderCards, LeaderListType);

        ArrayList<LeaderCardExt> leaderCards1 = gson.fromJson(message, LeaderListType);
        int i=0;
        for (LeaderCardExt card: leaderCards){
            assertEquals(card,leaderCards1.get(i));
            i++;
        }
    }

    @Test
    public void test() {
        ArrayList<LeaderCardExt> leaderCards = new ArrayList<>();
        leaderCards = Starter.LeaderCardsParser();

        String message= Starter.toJson(leaderCards.get(1), LeaderCardExt.class);

        System.out.println(message);

        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        Gson gson= builder.create();

        LeaderCardExt card=gson.fromJson(message, LeaderCardExt.class);
        assertEquals(leaderCards.get(1), card );
    }
}