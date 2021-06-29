package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Shelf;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.UnableToFillException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest{

    @Test
    public void DepotsAbilityTest() {
        DepotsAbility ability= new DepotsAbility(ResourceType.Coins);

        //test getter
        assertEquals(ability.getTypeOfRes(), ResourceType.Coins);

        //test equals
        assertEquals(ability, ability);
        assertEquals(ability, new DepotsAbility(ResourceType.Coins));
        assertNotEquals(ability, new DepotsAbility(ResourceType.Servants));
        assertNotEquals(ability, new Object());


        PlayerExt player= new PlayerExt("Pippo");
        try {
            player.getDepots().addResourcesFromMarket(new NumberOfResources(1,2,3,0));
        } catch (UnableToFillException e) {
            fail();
        }

        ability.RunAbility(player);

        assertEquals(4, player.getDepots().getWareHouseDepots().getShelves().size());
        for(Shelf x: player.getDepots().getWareHouseDepots().getShelves()){
            if(x.getIsExtra()){
                assertEquals(ResourceType.Coins,x.getResType());
            }
        }

        ability.RunAbility(player);

        assertEquals(5, player.getDepots().getWareHouseDepots().getShelves().size());
        for(Shelf x: player.getDepots().getWareHouseDepots().getShelves()){
            if(x.getIsExtra()){
                assertEquals(ResourceType.Coins,x.getResType());
            }
        }
    }

    @Test
    public void WhiteAbilityTest(){
        WhiteAbility ability= new WhiteAbility(ResourceType.Coins);
        WhiteAbility ability2= new WhiteAbility(ResourceType.Shields);

        //test getter
        assertEquals(ability.getTypeOfRes(), ResourceType.Coins);

        //test equals
        assertEquals(ability, ability);
        assertEquals(ability, new WhiteAbility(ResourceType.Coins));
        assertNotEquals(ability, new WhiteAbility(ResourceType.Servants));
        assertNotEquals(ability, new Object());

        PlayerExt player= new PlayerExt("Pippo");

        ability.RunAbility(player);
        ability2.RunAbility(player);

        assertEquals(2, player.getConverter().getToconvert().size());
        assertEquals(ResourceType.Coins, player.getConverter().getToconvert().get(0));
        assertEquals(ResourceType.Shields, player.getConverter().getToconvert().get(1));
    }

    @Test
    public void DiscountAbilityTest(){
        DiscountAbility ability=new DiscountAbility(ResourceType.Coins, 5);
        DiscountAbility ability2=new DiscountAbility(ResourceType.Servants, 2);
        PlayerExt player= new PlayerExt("Pippo");

        //test getter
        assertEquals(ability.getTypeOfRes(), ResourceType.Coins);
        assertEquals(5, ability.getDiscountAmount());

        //test equals
        assertEquals(ability, ability);
        assertEquals(ability, new DiscountAbility(ResourceType.Coins, 5));
        assertNotEquals(ability, new DiscountAbility(ResourceType.Coins, 3));
        assertNotEquals(ability, new DiscountAbility(ResourceType.Servants, 5));
        assertNotEquals(ability, new Object());

        ability.RunAbility(player);
        ability2.RunAbility(player);

        assertEquals(new NumberOfResources(2,0,5,0), player.getDiscounted());
    }

    @Test
    public void PowerProductionPlusAbilityTest(){
        ProductionPowerPlusAbility ability= new ProductionPowerPlusAbility(ResourceType.Coins);
        PlayerExt player= new PlayerExt("Pippo");

        //test getter
        assertEquals(ability.getTypeOfRes(), ResourceType.Coins);

        //test equals
        assertEquals(ability, ability);
        assertEquals(ability, new ProductionPowerPlusAbility(ResourceType.Coins));
        assertNotEquals(ability, new ProductionPowerPlusAbility(ResourceType.Servants));
        assertNotEquals(ability, new Object());

        ability.RunAbility(player);

        assertEquals(2, player.getPersonalBoard().getProduction().size());
    }
}
