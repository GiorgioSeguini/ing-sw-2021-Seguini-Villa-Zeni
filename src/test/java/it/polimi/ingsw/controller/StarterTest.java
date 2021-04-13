package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StarterTest {

    @Test
    public void DevCardParserTest(){
        ArrayList<DevelopmentCard> devcards = null;
        try {
            devcards = Starter.DevCardParser();
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

    @Test
    public void MarblesParser(){

        try {
            ArrayList<MarbleColor>marbles= Starter.MarblesParser();
            assertEquals(13,marbles.size());
            int whites=0;
            int blues=0;
            int yellows=0;
            int greys=0;
            int reds=0;
            int purples=0;
            for (MarbleColor x: marbles){
                switch (x){
                    case RED: reds++; break;
                    case BLUE:blues++; break;
                    case WHITE:whites++; break;
                    case GREY:greys++; break;
                    case PURPLE:purples++; break;
                    case YELLOW:yellows++; break;
                    default: fail();
                }
            }
            assertEquals(1,reds);
            assertEquals(2,blues);
            assertEquals(2,greys);
            assertEquals(2,purples);
            assertEquals(2,yellows);
            assertEquals(4,whites);

        } catch (IOException | ParseException e){
            fail();
        }


    }

    @Test
    public void SoloActionTokesnTest(){
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();
        try {
           tokens=Starter.TokensParser();
        } catch (IOException|ParseException e) {
            fail();
        }
        assertEquals(7,tokens.size());
        int move2=0;
        int moveshuffle=0;
        int discard2=0;
        for (SoloActionTokens x: tokens){
            if(x instanceof Move2){
                move2++;
            }
            else{
                if (x instanceof MoveShuffle){
                    moveshuffle++;
                }
                else{
                    if (x instanceof Discard2){
                        discard2++;
                    }
                    else{
                        fail();
                    }
                }
            }
        }
        assertEquals(1,moveshuffle);
        assertEquals(2,move2);
        assertEquals(4,discard2);
    }

}