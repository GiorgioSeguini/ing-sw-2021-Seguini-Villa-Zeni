package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Starter {

    private static Level ConvertStringToLevel(String levelapp){
        Level level=null;
        switch (levelapp){
            case "ONE": level=Level.ONE; break;
            case "TWO": level=Level.TWO; break;
            case "THREE": level=Level.THREE; break;
            default: throw new IllegalArgumentException();
        }
        return level;
    }

    private static ColorDevCard ConvertStringToColorDevCard(String colorapp){
        ColorDevCard color = null;
        switch (colorapp){
            case "BLUE": color=ColorDevCard.BLUE; break;
            case "YELLOW": color=ColorDevCard.YELLOW; break;
            case "GREEN": color=ColorDevCard.GREEN; break;
            case "PURPLE": color=ColorDevCard.PURPLE; break;
            default: throw new IllegalArgumentException();
        }
        return color;
    }

    private static NumberOfResources ConvertObjectToNumOfRes(JSONObject object, String objname){
        JSONObject NumOfRes=(JSONObject) object.get((objname));
        int servants= Math.toIntExact((Long) NumOfRes.get("Servants"));
        int shields= Math.toIntExact((Long) NumOfRes.get("Shields"));
        int coins= Math.toIntExact((Long) NumOfRes.get("Coins"));
        int stones= Math.toIntExact((Long) NumOfRes.get("Stones"));

        return new NumberOfResources(servants,shields,coins,stones);
    }

    public static ArrayList<DevelopmentCard> DevCardParser() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String filePath = new File("").getAbsolutePath();
        JSONArray array = (JSONArray) parser.parse(new FileReader(filePath + "/src/main/resources/DevCard.json"));
        ArrayList<DevelopmentCard> devcards = new ArrayList<>();
        for (Object x : array) {
            JSONObject DevCard = (JSONObject) x;
            Level level = Starter.ConvertStringToLevel((String) DevCard.get("Level"));
            ColorDevCard color = Starter.ConvertStringToColorDevCard((String) DevCard.get("CardColor"));
            NumberOfResources cost = Starter.ConvertObjectToNumOfRes(DevCard, "Cost");

            int victorypoints = Math.toIntExact((Long) DevCard.get("VictoryPoints"));
            JSONObject ProductionPower = (JSONObject) DevCard.get("ProductionPower");
            int yourchoicein = Math.toIntExact((Long) ProductionPower.get("YourChoiceIn"));
            int yourchoiceout = Math.toIntExact((Long) ProductionPower.get("YourChoiceOut"));
            NumberOfResources inres = Starter.ConvertObjectToNumOfRes(ProductionPower, "InRes");
            NumberOfResources outres = Starter.ConvertObjectToNumOfRes(ProductionPower, "OutRes");
            int faithpointsout = Math.toIntExact((Long) ProductionPower.get("FaithPointsOut"));

            it.polimi.ingsw.model.ProductionPower productionPower = new ProductionPower(faithpointsout, outres, inres, yourchoicein, yourchoiceout);

            devcards.add(new DevelopmentCard(level, color, cost, productionPower, victorypoints));

        }

        return devcards;
    }

    public static ArrayList<MarbleColor> MarblesParser() throws IOException, ParseException {
        JSONParser parser= new JSONParser();
        String filePath = new File("").getAbsolutePath();
        JSONObject x= (JSONObject)parser.parse(new FileReader(filePath + "/src/main/resources/Marbles.json"));

        ArrayList<MarbleColor> marbles= new ArrayList<>();
        JSONArray array=(JSONArray) x.get("Marbles");
        for (Object y: array){
         switch ((String) y){
             case "GREY": marbles.add(MarbleColor.GREY); break;
             case "BLUE": marbles.add(MarbleColor.BLUE); break;
             case "WHITE": marbles.add(MarbleColor.WHITE); break;
             case "PURPLE": marbles.add(MarbleColor.PURPLE); break;
             case "RED": marbles.add(MarbleColor.RED); break;
             case "YELLOW": marbles.add(MarbleColor.YELLOW); break;
             default: throw new IllegalArgumentException();
         }
        }
        return marbles;
    }

    public static ArrayList<SoloActionTokens> TokensParser() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String filePath = new File("").getAbsolutePath();
        JSONArray array = (JSONArray) parser.parse(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"));
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();

        for (Object token: array){
            switch ((String)token){
                case "MOVE2": tokens.add(new Move2()); break;
                case "MOVESHUFFLE": tokens.add(new MoveShuffle()); break;
                case "DISCARD2-GREEN": tokens.add((new Discard2(ColorDevCard.GREEN)));break;
                case "DISCARD2-YELLOW": tokens.add((new Discard2(ColorDevCard.YELLOW))); break;
                case "DISCARD2-PURPLE": tokens.add((new Discard2(ColorDevCard.PURPLE))); break;
                case "DISCARD2-BLUE": tokens.add((new Discard2(ColorDevCard.BLUE))); break;
                default: throw new IllegalArgumentException();
            }
        }
        return tokens;
    }

}
