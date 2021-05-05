package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.parse.Starter;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    @Test
    void emptyDashboard(){
        Dashboard d = new Dashboard(new ArrayList<>());
        for(ColorDevCard color : ColorDevCard.values())
            if(!d.isEmpty()) fail();

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

        assertTrue(d.isEmpty());



    }

    @Test
    void removeCard() {

        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 0));
        cards.add(new DevelopmentCard(Level.TWO, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 1));
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 2));
        cards.add(new DevelopmentCard(Level.THREE, ColorDevCard.BLUE, new NumberOfResources(), new ProductionPower(), 3));
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.GREEN, new NumberOfResources(), new ProductionPower(), 0));
        cards.add(new DevelopmentCard(Level.TWO, ColorDevCard.YELLOW, new NumberOfResources(), new ProductionPower(), 1));
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.PURPLE, new NumberOfResources(), new ProductionPower(), 2));


        Dashboard d = new Dashboard(cards);

        //getter test
        DevelopmentCard c1 = d.getTopDevCard(ColorDevCard.BLUE, Level.ONE);
        if(!cards.contains(c1)) fail();
        assertEquals(c1, d.getTopDevCard(ColorDevCard.BLUE, Level.ONE));

        //not modifying test
        cards.add(new DevelopmentCard(Level.ONE, ColorDevCard.YELLOW, new NumberOfResources(), new ProductionPower(), 0));
        assertNull(d.getTopDevCard(ColorDevCard.YELLOW, Level.ONE));


        //remove test e isEmpity test
        assertFalse(d.isEmpty());
        d.removeCard(ColorDevCard.BLUE, 4);
        assertTrue(d.isEmpty());

        try{
            d.removeCard(ColorDevCard.BLUE, 4);
            fail();
        }catch(IllegalArgumentException ignored){}

    }

    @Test
    void findDevCard() throws IOException, ParseException {
        //int starting_point = DevelopmentCard.nextId.get()+1;
        ArrayList<DevelopmentCard> developmentCards= Starter.DevCardParser();
        Dashboard dashboard= new Dashboard(developmentCards);
        Random rand= new Random();

        for (int i=0; i<48; i++){
            assertNotNull(dashboard.findDevCard(i));
        }

        for (int i=48; i<100; i++){
            assertNull(dashboard.findDevCard(i));
        }


        assertEquals(dashboard.findDevCard(5).getLevel(), Level.ONE);
        assertEquals(dashboard.findDevCard(16).getColor(), ColorDevCard.GREEN);

    }
}