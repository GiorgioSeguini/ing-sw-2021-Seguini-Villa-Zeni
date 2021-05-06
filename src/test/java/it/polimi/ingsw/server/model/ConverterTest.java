package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.HaveToChooseException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ConverterTest {

    @Test
    void CheckIntegrityToConvert() {
        Player player=new Player("Pippo");
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

    @Test
    public void convertAllTest(){
        Converter converter= new Converter(new Player("Pippo"));
        ArrayList<MarbleColor> marbles= new ArrayList<>();
        try {
            marbles= Starter.MarblesParser();
        } catch (FileNotFoundException e) {
            fail();
        }

        try {
            converter.convertAll(marbles);
        } catch (HaveToChooseException e) {
            fail();
        }

        assertEquals(new NumberOfResources(2,2,2,2),converter.getResources());
        assertEquals(1,converter.getOwner().getFaithTrack().getFaithPoints());

        converter.CleanConverter();
        assertEquals(new NumberOfResources(),converter.getResources());

        converter.setWhiteAbility(ResourceType.Stones);
        assertEquals(1, converter.getToconvert().size());

        try {
            converter.convertAll(marbles);
        } catch (HaveToChooseException e) {
            fail();
        }
        assertEquals(new NumberOfResources(2,2,2,6),converter.getResources());
        assertEquals(2,converter.getOwner().getFaithTrack().getFaithPoints());

        converter.CleanConverter();
        assertEquals(new NumberOfResources(),converter.getResources());

        try {
            converter.setWhiteAbility(ResourceType.Stones);
            fail();
        }
        catch (IllegalArgumentException e){ }
        assertEquals(1, converter.getToconvert().size());

        try {
            converter.setWhiteAbility(ResourceType.Coins);
        }
        catch (IllegalArgumentException e){
            fail();
        }
        assertEquals(2, converter.getToconvert().size());

        try {
            converter.convertAll(marbles);
            fail();
        } catch (HaveToChooseException e) {}
        assertEquals(new NumberOfResources(2,2,2,2),converter.getResources());
        assertEquals(3,converter.getOwner().getFaithTrack().getFaithPoints());


    }

    @Test
    public void setResourcesTest(){
        Player player= new Player("Pippo");
        NumberOfResources x= new NumberOfResources(1,2,3,4);

        player.getConverter().setResources(x);
        assertEquals(new NumberOfResources(1,2,3,4), player.getConverter().getResources());
    }

    @Test
    public void IsWhiteAbilityActiveTest(){
        Player player= new Player("Pippo");
        WhiteAbility whiteAbility= new WhiteAbility(ResourceType.Coins);

        whiteAbility.RunAbility(player);

        assertTrue(player.getConverter().IsWhiteAbilityActive());

        Converter converter= new Converter(player);

        assertFalse(converter.IsWhiteAbilityActive());

    }

    @Test
    public void WhiteMarbleConverterTest(){
        Player player= new Player("Pippo");

        ArrayList<ResourceType> whites= new ArrayList<>();
        for(ResourceType x: ResourceType.values()){
            whites.add(x);
        }

        player.getConverter().WhiteMarbleConverter(whites);
        assertEquals(new NumberOfResources(1,1,1,1), player.getConverter().getResources());
    }
}


