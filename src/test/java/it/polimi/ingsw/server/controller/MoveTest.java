package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.move.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    @Test
    public void classNameTest(){
        ArrayList<String> names = new ArrayList<>();
        assertNotNull(new MoveActiveProduction(0).getClassName());
        assertEquals(new MoveActiveProduction(0).getClassName(), MoveActiveProduction.className);
        assertFalse(names.contains(MoveActiveProduction.className));
        names.add(MoveActiveProduction.className);
        assertNotNull(new MoveBuyDevCard(0).getClassName());
        assertEquals(new MoveBuyDevCard(0).getClassName(), MoveBuyDevCard.className);
        assertFalse(names.contains(MoveBuyDevCard.className));
        names.add(MoveBuyDevCard.className);
        assertNotNull(new MoveChoseInitialResources(0).getClassName());
        assertEquals(new MoveChoseInitialResources(0).getClassName(), MoveChoseInitialResources.className);
        assertFalse(names.contains(MoveChoseInitialResources.className));
        names.add(MoveChoseInitialResources.className);
        assertNotNull(new MoveChoseLeaderCards(0).getClassName());
        assertEquals(new MoveChoseLeaderCards(0).getClassName(), MoveChoseLeaderCards.className);
        assertFalse(names.contains(MoveChoseLeaderCards.className));
        names.add(MoveChoseLeaderCards.className);
        assertNotNull(new MoveChoseResources(0).getClassName());
        assertEquals(new MoveChoseResources(0).getClassName(), MoveChoseResources.className);
        assertFalse(names.contains(MoveChoseResources.className));
        names.add(MoveChoseResources.className);
        assertNotNull(new MoveDiscardResources(0).getClassName());
        assertEquals(new MoveDiscardResources(0).getClassName(), MoveDiscardResources.className);
        assertFalse(names.contains(MoveDiscardResources.className));
        names.add(MoveDiscardResources.className);
        assertNotNull(new MoveEndTurn(0).getClassName());
        assertEquals(new MoveEndTurn(0).getClassName(), MoveEndTurn.className);
        assertFalse(names.contains(MoveEndTurn.className));
        names.add(MoveEndTurn.className);
        assertNotNull(new MoveLeader(0).getClassName());
        assertEquals(new MoveLeader(0).getClassName(), MoveLeader.className);
        assertFalse(names.contains(MoveLeader.className));
        names.add(MoveLeader.className);
        assertNotNull(new MoveWhiteConversion(0).getClassName());
        assertEquals(new MoveWhiteConversion(0).getClassName(), MoveWhiteConversion.className);
        assertFalse(names.contains(MoveWhiteConversion.className));
        names.add(MoveWhiteConversion.className);
        assertNotNull(new MoveTypeMarket(0).getClassName());
        assertEquals(new MoveTypeMarket(0).getClassName(), MoveTypeMarket.className);
        assertFalse(names.contains(MoveTypeMarket.className));
        names.add(MoveTypeMarket.className);
    }
}
