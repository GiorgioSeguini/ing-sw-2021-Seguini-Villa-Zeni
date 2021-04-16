package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Starter;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AbilityTest{

    @Test
    public void DepotsAbilityTest() {
        DepotsAbility ability= new DepotsAbility(ResourceType.Coins);

        Player player= new Player("Pippo", null, 0, new NumberOfResources(1,2,3,0));

        ability.RunAbility(player);

        assertEquals(4, player.getDepots().getWareHouseDepots().getShelfs().size());
        for(Shelf x: player.getDepots().getWareHouseDepots().getShelfs()){
            if(x.is_extra_shelf){
                assertEquals(ResourceType.Coins,x.getResType());
            }
        }

        ability.RunAbility(player);

        assertEquals(5, player.getDepots().getWareHouseDepots().getShelfs().size());
        for(Shelf x: player.getDepots().getWareHouseDepots().getShelfs()){
            if(x.is_extra_shelf){
                assertEquals(ResourceType.Coins,x.getResType());
            }
        }
    }

    @Test
    public void WhiteAbilityTest(){
        WhiteAbility ability= new WhiteAbility(ResourceType.Coins);
        WhiteAbility ability2= new WhiteAbility(ResourceType.Shields);

        Player player= new Player("Pippo", null, 0, new NumberOfResources());

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
        Player player= new Player("Pippo", null, 0, new NumberOfResources());

        ability.RunAbility(player);
        ability2.RunAbility(player);

        assertEquals(new NumberOfResources(2,0,5,0), player.getDiscount());
    }

    @Test
    public void PowerProductionPlusAbilityTest(){
        ProductionPowerPlusAbility ability= new ProductionPowerPlusAbility(ResourceType.Coins);
        Player player=null;
        try {
            player= new Player("Pippo", new PersonalBoard(Starter.LeaderCardsParser().toArray(new LeaderCard[Starter.LeaderCardsParser().size()])), 0, new NumberOfResources());
        } catch (IOException| ParseException e) {
            fail();
        }
        ability.RunAbility(player);

        assertEquals(2, player.getPersonalBoard().getProduction().size());
    }
}
