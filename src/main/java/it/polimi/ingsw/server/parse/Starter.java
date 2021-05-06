package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import it.polimi.ingsw.server.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Starter {

    private static int NUMOFMARBELS=13;
    private static int NUMOFDEVCARD=48;
    private static int NUMOFLEADERCARD=16;
    private static int NUMOFTOKENS=7;

    /*Methods to Initialize the Game*/
    public static ArrayList<LeaderCard> LeaderCardsParser() throws FileNotFoundException {
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        builder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        Gson gson=builder.create();
        Type LeaderListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
        String filePath = new File("").getAbsolutePath();
        return gson.fromJson(new FileReader(filePath + "/src/main/resources/LeaderCards.json"), LeaderListType);
    }

    public static ArrayList<SoloActionTokens> TokensParser() throws FileNotFoundException{
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        Gson gson=builder.create();

        Type TokensListType = new TypeToken<ArrayList<SoloActionTokens>>(){}.getType();
        String filePath = new File("").getAbsolutePath();
        return gson.fromJson(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"), TokensListType);
    }

    public static ArrayList<MarbleColor> MarblesParser() throws FileNotFoundException {
        Gson gson= new Gson();
        String filePath = new File("").getAbsolutePath();

        Type marblesarray= new TypeToken<ArrayList<MarbleColor>>(){}.getType();

        return gson.fromJson(new FileReader(filePath+"/src/main/resources/Marbles.json"), marblesarray);
    }

    public static ArrayList<DevelopmentCard> DevCardParser() throws FileNotFoundException {
        Gson gsonExt;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        gsonExt = builder.create();
        Type devCardListType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        String filePath = new File("").getAbsolutePath();
        return gsonExt.fromJson(new FileReader(filePath + "/src/main/resources/DevCard.json"), devCardListType);
    }


    /*Methods for CanPerform*/
    public static boolean CanParseMarbles() {
        Gson gson= new Gson();
        String filePath = new File("").getAbsolutePath();
        Type marblesarray= new TypeToken<ArrayList<MarbleColor>>(){}.getType();
        ArrayList<MarbleColor> marbles= new ArrayList<>();
        try {
            marbles=gson.fromJson(new FileReader(filePath + "/src/main/resources/Marbles.json"),marblesarray);
        } catch (FileNotFoundException e) {
            return false;
        }

        if(marbles.size()!=NUMOFMARBELS){
            return false;
        }
        return true;
    }

    public static boolean CanDevCardParser(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        Gson gson= gsonBuilder.create();

        String filePath = new File("").getAbsolutePath();
        Type devCardListType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        ArrayList<DevelopmentCard> developmentCards=new ArrayList<>();

        try {
            developmentCards=gson.fromJson(new FileReader(filePath + "/src/main/resources/DevCard.json"), devCardListType);
        } catch (FileNotFoundException e) {
            return false;
        }

        if(developmentCards.size()!=NUMOFDEVCARD){
            return false;
        }

        return true;
    }

    public static boolean CanLeaderCardsParser(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        gsonBuilder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        Gson gson= gsonBuilder.create();

        String filePath = new File("").getAbsolutePath();
        Type LeaderListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();
        ArrayList<LeaderCard> leaderCards=new ArrayList<>();

        try {
            leaderCards=gson.fromJson(new FileReader(filePath + "/src/main/resources/LeaderCards.json"), LeaderListType);
        } catch (FileNotFoundException e) {
            return false;
        }

        if( leaderCards.size()!=NUMOFLEADERCARD){
            return false;
        }
        return true;
    }

    public static boolean CanTokensParser(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        Gson gson= gsonBuilder.create();

        String filePath = new File("").getAbsolutePath();
        Type TokensListType = new TypeToken<ArrayList<SoloActionTokens>>(){}.getType();
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();

        try {
            tokens=gson.fromJson(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"), TokensListType);
        } catch (FileNotFoundException e) {
            return false;
        }

        if(tokens.size()!=NUMOFTOKENS){
            return false;
        }

        return true;
    }
}
