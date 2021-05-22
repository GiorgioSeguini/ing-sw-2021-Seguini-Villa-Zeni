package it.polimi.ingsw.server.parse;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StarterTest {

    @Test
    public void DevCardParserTest(){
        ArrayList<DevelopmentCardExt> devcards = new ArrayList<>();
        try {
            devcards = Starter.DevCardParser();
        } catch (IOException e) {
            fail();
        }

        DashboardExt dashboard = new DashboardExt(devcards);

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

        } catch (FileNotFoundException e){
            fail();
        }


    }

    @Test
    public void SoloActionTokesnTest(){
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();
        try {
           tokens=Starter.TokensParser();
        } catch (FileNotFoundException e) {
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

    @Test
    public void LeaderCardsParserTest(){
        ArrayList<LeaderCardExt>leaderCards=new ArrayList<>();
        try {
            leaderCards=Starter.LeaderCardsParser();
        } catch (IOException  e) {
            fail();
        }
        assertEquals(16,leaderCards.size());

        for(LeaderCardExt x: leaderCards){
            assertNotNull(x.getAbility());
            assertNotNull(x.getVictoryPoints());
            assertNotNull(x.getRequirements());
            assertNotNull(x.getStatus());
        }
    }

    @Test
    public void CanLeaderCardParserTest(){
        assertTrue(Starter.CanLeaderCardsParser());
    }

    @Test
    public void CanDevCardParserTest(){
        assertTrue(Starter.CanDevCardParser());
    }

    @Test
    public void CanMarblesParserTest(){
        assertTrue(Starter.CanParseMarbles());
    }

    @Test
    public void CanSoloActionTokensTest(){
        assertTrue(Starter.CanTokensParser());
    }

}