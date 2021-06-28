package it.polimi.ingsw.server.model;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.HaveToChooseException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("ALL")
public class ConverterTest {

    @Test
    void CheckIntegrityToConvert() {
        PlayerExt player=new PlayerExt("Pippo");
        ConverterExt x=new ConverterExt(player);
        assertFalse(x.isWhiteAbilityActive());
        x.setWhiteAbility(ResourceType.Stones);
        x.setWhiteAbility(ResourceType.Coins);
        assertTrue(x.isWhiteAbilityActive());

        try{
            x.setWhiteAbility(ResourceType.Shields);
            fail();
        }catch (IllegalArgumentException ignored){}
        assertEquals(player.getID(), x.getOwnerId());
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
        assertFalse(x.CheckIntegrityToConvert(test));

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
        ConverterExt converter= new ConverterExt(new PlayerExt("Pippo"));
        ArrayList<MarbleColor> marbles= new ArrayList<>();
        marbles= Starter.MarblesParser();

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
        catch (IllegalArgumentException ignored){ }
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
        } catch (HaveToChooseException ignored) {}
        assertEquals(new NumberOfResources(2,2,2,2),converter.getResources());
        assertEquals(3,converter.getOwner().getFaithTrack().getFaithPoints());


    }

    @Test
    public void setResourcesTest(){
        PlayerExt player= new PlayerExt("Pippo");
        NumberOfResources x= new NumberOfResources(1,2,3,4);

        player.getConverter().setResources(x);
        assertEquals(new NumberOfResources(1,2,3,4), player.getConverter().getResources());
    }

    @Test
    public void IsWhiteAbilityActiveTest(){
        PlayerExt player= new PlayerExt("Pippo");
        WhiteAbility whiteAbility= new WhiteAbility(ResourceType.Coins);

        whiteAbility.RunAbility(player);

        assertTrue(player.getConverter().IsWhiteAbilityActive());

        ConverterExt converter= new ConverterExt(player);

        assertFalse(converter.IsWhiteAbilityActive());

    }

    @Test
    public void WhiteMarbleConverterTest(){
        PlayerExt player= new PlayerExt("Pippo");

        ArrayList<ResourceType> whites = new ArrayList<>(Arrays.asList(ResourceType.values()));

        player.getConverter().WhiteMarbleConverter(whites);
        assertEquals(new NumberOfResources(1,1,1,1), player.getConverter().getResources());
    }
}


