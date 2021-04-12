package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.ProductionPower;
import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    public void DevCardParser() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String filePath = new File("").getAbsolutePath();
        JSONArray array= (JSONArray) parser.parse(new FileReader(filePath + "/src/main/resources/DevCard.json"));
        ArrayList<DevelopmentCard> devcards=new ArrayList<>();
        for(Object x: array){
            JSONObject DevCard= (JSONObject) x;
            Level level=Parser.ConvertStringToLevel((String) DevCard.get("Level"));
            ColorDevCard color= Parser.ConvertStringToColorDevCard((String) DevCard.get("CardColor"));
            NumberOfResources cost= Parser.ConvertObjectToNumOfRes(DevCard,"Cost");

            int victorypoints=Math.toIntExact((Long)DevCard.get("VictoryPoints"));
            JSONObject ProductionPower=(JSONObject) DevCard.get("ProductionPower");
            int yourchoicein=Math.toIntExact((Long)ProductionPower.get("YourChoiceIn"));
            int yourchoiceout=Math.toIntExact((Long)ProductionPower.get("YourChoiceOut"));
            NumberOfResources inres= Parser.ConvertObjectToNumOfRes(ProductionPower,"InRes");
            NumberOfResources outres= Parser.ConvertObjectToNumOfRes(ProductionPower,"OutRes");
            int faithpointsout= Math.toIntExact((Long)ProductionPower.get("FaithPointsOut"));

            it.polimi.ingsw.model.ProductionPower productionPower=new ProductionPower(faithpointsout,outres,inres,yourchoicein,yourchoiceout);

            devcards.add(new DevelopmentCard(level,color,cost,productionPower,victorypoints));

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