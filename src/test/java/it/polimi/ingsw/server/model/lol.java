package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.server.parse.TokensSerializer;
import org.junit.jupiter.api.Test;

public class lol {

    @Test
    public void test(){
        Move2 move2= new Move2();
        Discard2 discard2= new Discard2(ColorDevCard.BLUE);
        MoveShuffle moveShuffle =new MoveShuffle();
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        Gson gson=builder.create();

        String tosend=gson.toJson(move2)+gson.toJson(discard2)+gson.toJson(moveShuffle);

        System.out.println(tosend);
    }// TODO: 5/5/21 non capisco delle cose
}
