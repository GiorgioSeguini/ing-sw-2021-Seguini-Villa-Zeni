package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalBoardTest {

    @Test
    void test(){

        PlayerExt player = new PlayerExt("Pippo");
        PersonalBoardExt p = player.getPersonalBoard();
        LeaderCardExt[] cards = new LeaderCardExt[]{new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 8), new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Stones), 8),};

        try{
            p.addLeaderCard(new LeaderCardExt[3]);
            fail();
        }catch(IllegalArgumentException ignored){}

        p.addLeaderCard(cards);

        try{
            p.addLeaderCard(cards);
            fail();
        } catch (IllegalArgumentException ignored){}

        assertEquals(1, p.getProduction().size());

        try{
            p.addDevCard(new DevelopmentCardExt(Level.ONE, ColorDevCard.GREEN, new NumberOfResources(), new ProductionPowerExt(), 0), -2);
            fail();
        }catch(IllegalArgumentException ignored){}
        catch(NoSpaceException e){
            fail();
        }

        assertEquals(0, p.getVictoryPoints());

        try {
            p.addDevCard(new DevelopmentCardExt(Level.ONE, ColorDevCard.GREEN, new NumberOfResources(), new ProductionPowerExt(), 3), 0);
        }catch(NoSpaceException e){
            fail();
        }

        assertEquals(2, p.getProduction().size());
        assertEquals(3, p.getVictoryPoints());

        //try{
            assertEquals(2, p.getLeaderCards().size());
            cards[0].setDiscard(player);
            assertEquals(1, p.getLeaderCards().size());
        /*}catch(NoMoreLeaderCardAliveException e){
            fail();
        }*/

        cards[1].setDiscard(player);
        /*try{
            p.getLeaderCards();
            fail();
        }catch(NoMoreLeaderCardAliveException ignored){}
        */
    }

    @Test
    void test2(){
        PlayerExt player = new PlayerExt("Pippo");
        PersonalBoardExt p = player.getPersonalBoard();
        LeaderCardExt[] cards = new LeaderCardExt[]{new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Coins), 8), new LeaderCardExt(new RequirementsExt(), new WhiteAbility(ResourceType.Stones), 8),};


        p.addLeaderCard(cards);

        //try{
            assertEquals(2, p.getLeaderCards().size());
            System.out.println(p);
            cards[1].setDiscard(player);
            assertEquals(1, p.getLeaderCards().size());
        /*}catch(NoMoreLeaderCardAliveException e){
            fail();
        }*/


        try {
            p.addDevCard(new DevelopmentCardExt(Level.ONE, ColorDevCard.GREEN, new NumberOfResources(), new ProductionPowerExt(), 3), 0);
        }catch(NoSpaceException e){
            fail();
        }

        try {
            p.addDevCard(new DevelopmentCardExt(Level.THREE, ColorDevCard.GREEN, new NumberOfResources(), new ProductionPowerExt(), 3), 0);
            fail();
        }catch(NoSpaceException e){
            assertTrue(true);
        }

    }
}
