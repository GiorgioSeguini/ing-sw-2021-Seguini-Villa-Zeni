package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.ColorDevCard;
import it.polimi.ingsw.model.enumeration.Level;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.ResourceType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

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

    private static NumberOfResources ConvertObjectToNumOfRes(JSONObject NumOfRes){
        int servants= Math.toIntExact((Long) NumOfRes.get("Servants"));
        int shields= Math.toIntExact((Long) NumOfRes.get("Shields"));
        int coins= Math.toIntExact((Long) NumOfRes.get("Coins"));
        int stones= Math.toIntExact((Long) NumOfRes.get("Stones"));

        return new NumberOfResources(servants,shields,coins,stones);
    }

    private static ResourceType ConvertStringToResType(String resource){
        switch (resource){
            case "STONES": return ResourceType.Stones;
            case "SERVANTS": return ResourceType.Servants;
            case "SHIELDS": return ResourceType.Shields;
            case "COINS": return ResourceType.Coins;
            default: throw new IllegalArgumentException();
        }
    }

    private static Ability getAbilityFromObject(JSONObject ability){
        try{
            return new DepotsAbility(ConvertStringToResType((String) ability.get("DepotsAbility")));
        } catch (NullPointerException e){};
        try{
            return new DiscountAbility(ConvertStringToResType((String) ability.get("DiscountAbility")),1);
        } catch (NullPointerException e){};
        try{
            return new WhiteAbility(ConvertStringToResType((String) ability.get("WhiteAbility")));
        } catch (NullPointerException e){};
        try{
            return new ProductionPowerPlusAbility(ConvertStringToResType((String) ability.get("ProductionPlusAbility")));
        } catch (NullPointerException e){};
        return null;
    }

    private static Requirements getRequirementsFromObject(JSONObject requirementsx) {
        try {
            JSONArray cardsReq= (JSONArray) requirementsx.get("CardsReq");
            ArrayList<Map.Entry<ColorDevCard,Level>> cardreqs= new ArrayList<>();
            for (Object x: cardsReq){
                JSONObject couple=(JSONObject) x;
                ColorDevCard color=ConvertStringToColorDevCard((String) couple.get("Color"));
                Level level= ConvertStringToLevel((String) couple.get("Level"));
                cardreqs.add(new AbstractMap.SimpleEntry<ColorDevCard,Level>(color,level));
            }
            return new Requirements(cardreqs);
        }catch (NullPointerException e){}

        try {
            JSONObject Resources=(JSONObject) requirementsx.get("Resources") ;
            NumberOfResources resources= ConvertObjectToNumOfRes(Resources);
            return new Requirements(resources);

        }catch (NullPointerException e){}

        throw new IllegalArgumentException();
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
            NumberOfResources cost = Starter.ConvertObjectToNumOfRes((JSONObject) DevCard.get("Cost"));

            int victorypoints = Math.toIntExact((Long) DevCard.get("VictoryPoints"));
            JSONObject ProductionPower = (JSONObject) DevCard.get("ProductionPower");
            int yourchoicein = Math.toIntExact((Long) ProductionPower.get("YourChoiceIn"));
            int yourchoiceout = Math.toIntExact((Long) ProductionPower.get("YourChoiceOut"));
            NumberOfResources inres = Starter.ConvertObjectToNumOfRes((JSONObject) ProductionPower.get("InRes"));
            NumberOfResources outres = Starter.ConvertObjectToNumOfRes((JSONObject) ProductionPower.get("OutRes"));
            int faithpointsout = Math.toIntExact((Long) ProductionPower.get("FaithPointsOut"));

            ProductionPower productionPower = new ProductionPower(faithpointsout, outres, inres, yourchoicein, yourchoiceout);

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

    public static ArrayList<LeaderCard> LeaderCardsParser() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String filePath = new File("").getAbsolutePath();
        JSONArray array = (JSONArray) parser.parse(new FileReader(filePath + "/src/main/resources/LeaderCards.json"));
        ArrayList<LeaderCard> leaderCards=new ArrayList<>();

        for ( Object x: array){
            JSONObject card=(JSONObject) x;
            int victorypoints = Math.toIntExact((Long) card.get("VictoryPoints"));
            JSONObject abilityapp=(JSONObject) card.get("Ability");
            Ability ability= getAbilityFromObject(abilityapp);
            JSONObject reqapp= (JSONObject)card.get("Requirements");
            Requirements requirements=getRequirementsFromObject(reqapp);
            leaderCards.add(new LeaderCard(requirements,ability,victorypoints));
        }

        return leaderCards;
    }

}
