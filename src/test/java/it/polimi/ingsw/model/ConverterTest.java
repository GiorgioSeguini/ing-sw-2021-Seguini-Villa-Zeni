package it.polimi.ingsw.model;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ConverterTest {

    @Test
    void CheckIntegrityToConvert() {
        Player player=new Player("Pippo", new PersonalBoard(new LeaderCard[3]),5,new NumberOfResources());
        Converter x=new Converter(player);
        x.setWhiteAbility(ResourceType.Stones);
        x.setWhiteAbility(ResourceType.Coins);

        ArrayList<ResourceType> test= new ArrayList<>();
        test.add(ResourceType.Stones);
        test.add(ResourceType.Coins);
        test.add(ResourceType.Stones);
        test.add(ResourceType.Stones);
        test.add(ResourceType.Coins);
        test.add(ResourceType.Stones);
        test.add(ResourceType.Stones);
        test.add(ResourceType.Coins);
        test.add(ResourceType.Stones);
        assertTrue(x.CheckIntegrityToConvert(test));

        test= new ArrayList<>();
        assertTrue(x.CheckIntegrityToConvert(test));

        test= new ArrayList<>();
        test.add(ResourceType.Stones);
        test.add(ResourceType.Coins);
        test.add(ResourceType.Stones);
        test.add(ResourceType.Servants);
        test.add(ResourceType.Coins);
        test.add(ResourceType.Stones);
        test.add(ResourceType.Stones);
        test.add(ResourceType.Coins);
        test.add(ResourceType.Stones);
        assertFalse(x.CheckIntegrityToConvert(test));

        test= new ArrayList<>();
        test.add(ResourceType.Servants);
        assertFalse(x.CheckIntegrityToConvert(test));
    }
}


