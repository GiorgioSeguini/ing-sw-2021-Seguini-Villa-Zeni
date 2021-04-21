package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    @Test
    void emptyDashboard(){
        Dashboard d = new Dashboard(new ArrayList<>());
        for(ColorDevCard color : ColorDevCard.values())
            if(!d.isEmpty(color)) fail();

        try{
            for(ColorDevCard color : ColorDevCard.values())
                for(Level l : Level.values())
                    assertNull(d.getTopDevCard(color, l));
        }catch(NullPointerException e){
            fail();
        }


    }

    @Test
    void oneStackDashBoard() {
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 0));
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 1));
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 2));
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 3));

        Dashboard d = new Dashboard(cards);

        //getter test
        DevelopmentCard c1 = d.getTopDevCard(ColorDevCard.BLUE, Level.ONE);
        if(!cards.contains(c1)) fail();
        assertEquals(c1, d.getTopDevCard(ColorDevCard.BLUE, Level.ONE));

        //isEmptyTest
        assertFalse(d.isEmpty(ColorDevCard.BLUE));
        for(ColorDevCard color : ColorDevCard.values())
            if(!color.equals(ColorDevCard.BLUE))
                assertTrue(d.isEmpty(color));

        //buy Test
        assertTrue(cards.remove(d.buyDevCard(ColorDevCard.BLUE, Level.ONE)));
        assertTrue(cards.remove(d.buyDevCard(ColorDevCard.BLUE, Level.ONE)));
        assertTrue(cards.remove(d.buyDevCard(ColorDevCard.BLUE, Level.ONE)));
        assertTrue(cards.remove(d.buyDevCard(ColorDevCard.BLUE, Level.ONE)));

        assertTrue(cards.isEmpty());

        try{
            d.buyDevCard(ColorDevCard.BLUE, Level.ONE);
            fail();
        }catch(IllegalArgumentException ignored){}

        assertTrue(d.isEmpty(ColorDevCard.BLUE));



    }

    @Test
    void removeCard() {

        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 0));
        cards.add(new DevelopmentCard(Level.TWO, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 1));
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 2));
        cards.add(new DevelopmentCard(Level.THREE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 3));

        Dashboard d = new Dashboard(cards);

        //getter test
        DevelopmentCard c1 = d.getTopDevCard(ColorDevCard.BLUE, Level.ONE);
        if(!cards.contains(c1)) fail();
        assertEquals(c1, d.getTopDevCard(ColorDevCard.BLUE, Level.ONE));

        //not modifying test
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.YELLOW, new NumberOfResources(), new ProductionPower(), 0));
        assertNull(d.getTopDevCard(ColorDevCard.YELLOW, Level.ONE));


        //remove test
        assertFalse(d.isEmpty(ColorDevCard.BLUE));
        d.removeCard(ColorDevCard.BLUE, 4);
        assertTrue(d.isEmpty(ColorDevCard.BLUE));

        try{
            d.removeCard(ColorDevCard.BLUE, 4);
            fail();
        }catch(IllegalArgumentException ignored){}

    }
}