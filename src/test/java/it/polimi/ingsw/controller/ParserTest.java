package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    public void DevCardParserTest(){
        ArrayList<DevelopmentCard> devcards = null;
        try {
            devcards = Parser.DevCardParser();
        } catch (IOException | ParseException e) {
            fail();
        }

        for(DevelopmentCard card : devcards){
            System.out.println("CARD "+ devcards.indexOf(card));
            System.out.println(card+"\n");
        }
        assertTrue(true);

        Dashboard dashboard = new Dashboard(devcards);

        for(Level l: Level.values()){
            for(ColorDevCard c: ColorDevCard.values()){
                for(int i=0; i<4; i++) {
                    assertNotNull(dashboard.buyDevCard(c, l));
                }
                try{
                    dashboard.buyDevCard(c, l);
                    fail();
                }catch(IllegalArgumentException ignored){}
            }
        }

    }

}