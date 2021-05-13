package it.polimi.ingsw.server.parse;

import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.server.model.Depots;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class DepotsTest {

    @Test
    public void test(){
        Depots depots = new Depots(0);

        String msg =Starter.toJson(depots, Depots.class);

        try{
            StarterClient.fromJson(msg, it.polimi.ingsw.client.modelClient.Depots.class);
        }catch(Exception e){
            fail();
        }
    }
}
