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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    public static Level ConvertStringToLevel(String levelapp){
        Level level=null;
        switch (levelapp){
            case "ONE": level=Level.ONE; break;
            case "TWO": level=Level.TWO; break;
            case "THREE": level=Level.THREE; break;
            default: throw new IllegalArgumentException();
        }
        return level;
    }

    public static ColorDevCard ConvertStringToColorDevCard(String colorapp){
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

    public static NumberOfResources ConvertObjectToNumOfRes(JSONObject object, String objname){
        JSONObject NumOfRes=(JSONObject) object.get((objname));
        int servants= Math.toIntExact((Long) NumOfRes.get("Servants"));
        int shields= Math.toIntExact((Long) NumOfRes.get("Shields"));
        int coins= Math.toIntExact((Long) NumOfRes.get("Coins"));
        int stones= Math.toIntExact((Long) NumOfRes.get("Stones"));

        return new NumberOfResources(servants,shields,coins,stones);
    }

}
