package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.Ability;
import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.SoloActionTokens;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AbilitySerializerTest {

    /*
    @Test
    public void testx() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        try {
            leaderCards = Starter.LeaderCardsParser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        gsonBuilder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson = gsonBuilder.create();

        System.out.print("[");
        for (LeaderCard card : leaderCards) {
            System.out.print(gson.toJson(card) + ",");
        }
        System.out.print("]");
        Type LeaderListType = new TypeToken<ArrayList<LeaderCard>>() {
        }.getType();
        String mesage = gson.toJson(leaderCards, LeaderListType);

        ArrayList<LeaderCard> leaderCards1 = gson.fromJson(mesage, LeaderListType);

        System.out.println("lol");
    } // TODO: 5/5/21 da sistemare
      */

}
