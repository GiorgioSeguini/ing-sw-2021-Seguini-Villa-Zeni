package it.polimi.ingsw.controller;

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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;

public class Parser {

    public Level ConvertStringToLevel(String levelapp){
        Level level=null;
        switch (levelapp){
            case "ONE": level=Level.ONE; break;
            case "TWO": level=Level.TWO; break;
            case "THREE": level=Level.THREE; break;
            default: throw new IllegalArgumentException();
        }
        return level;
    }

    public ColorDevCard ConvertStringToColorDevCard(String colorapp){
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

    public NumberOfResources ConvertObjectToNumOfRes(JSONObject object, String objname){
        JSONObject NumOfRes=(JSONObject) object.get((objname));
        int servants= Math.toIntExact((Long) NumOfRes.get("Servants"));
        int shields= Math.toIntExact((Long) NumOfRes.get("Shields"));
        int coins= Math.toIntExact((Long) NumOfRes.get("Coins"));
        int stones= Math.toIntExact((Long) NumOfRes.get("Stones"));

        return new NumberOfResources(servants,shields,coins,stones);
    }

    @Test
    public void DevCardParser() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray array= (JSONArray) parser.parse(new FileReader("/home/williamzeni/Projects/IngegneriaSoftware/github/src/main/java/it/polimi/ingsw/controller/DevCard.json"));
        ArrayList<DevelopmentCard> devcards=new ArrayList<>();
        for(Object x: array){
            JSONObject DevCard= (JSONObject) x;
            Level level=ConvertStringToLevel((String) DevCard.get("Level"));
            ColorDevCard color= ConvertStringToColorDevCard((String) DevCard.get("CardColor"));
            NumberOfResources cost= ConvertObjectToNumOfRes(DevCard,"Cost");

            int victorypoints=Math.toIntExact((Long)DevCard.get("VictoryPoints"));
            JSONObject ProductionPower=(JSONObject) DevCard.get("ProductionPower");
            int yourchoicein=Math.toIntExact((Long)ProductionPower.get("YourChoiceIn"));
            int yourchoiceout=Math.toIntExact((Long)ProductionPower.get("YourChoiceOut"));
            NumberOfResources inres=ConvertObjectToNumOfRes(ProductionPower,"InRes");
            NumberOfResources outres=ConvertObjectToNumOfRes(ProductionPower,"OutRes");
            int faithpointsout= Math.toIntExact((Long)ProductionPower.get("FaithPointsOut"));

            ProductionPower productionPower=new ProductionPower(faithpointsout,outres,inres,yourchoicein,yourchoiceout);

            devcards.add(new DevelopmentCard(level,color,cost,productionPower,victorypoints));

        }

        printDevCards(devcards);
        
    }

    public void printDevCards(ArrayList<DevelopmentCard> developmentCards){
        int i=-1;
        for(DevelopmentCard x: developmentCards){
            i++;
            System.out.println("\nCARD "+i);
            System.out.println("Level: "+x.getLevel());
            System.out.println("Color: "+x.getColor());
            System.out.println("VictoryPoints: "+x.getVictoryPoints());
            System.out.println("Cost");
            for(ResourceType y: ResourceType.values()){
                System.out.println("\t"+y+": "+x.getCost().getAmountOf(y));
            }
            System.out.println("Production Power");
            System.out.println("\tInput Resources");
            for(ResourceType y: ResourceType.values()){
                System.out.println("\t\t"+y+": "+x.getProductionPower().getInputRes().getAmountOf(y));
            }
            System.out.println("\tOutput Resources");
            for(ResourceType y: ResourceType.values()){
                System.out.println("\t\t"+y+": "+x.getProductionPower().getOutputRes().getAmountOf(y));
            }
            System.out.println("\t\tFaithPoints out: "+x.getProductionPower().getFaithPointsOut());
            System.out.println("\t\tYourChoiceIn: "+x.getProductionPower().getOfYourChoiceInput());
            System.out.println("\t\tYourChoiceOut: "+x.getProductionPower().getOfYourChoiceOutput());
        }
    }


}
