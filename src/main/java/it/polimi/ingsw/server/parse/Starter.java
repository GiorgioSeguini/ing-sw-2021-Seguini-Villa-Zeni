package it.polimi.ingsw.server.parse;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constant.parse.MessageSerializer;
import it.polimi.ingsw.client.modelClient.TokenType;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.MarbleColor;

import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.*;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.parse.MoveTypeSerializer;
import it.polimi.ingsw.constant.parse.NumberOfResSerializer;
import it.polimi.ingsw.constant.parse.SetupperSerializer;
import it.polimi.ingsw.constant.setupper.SetUp;
import it.polimi.ingsw.server.controller.Performable;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.network.Settable;

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

    private static final Gson gson;
    private static final String filePath;

    static{
        GsonBuilder builder= new GsonBuilder();
        builder.registerTypeAdapter(NumberOfResources.class, new NumberOfResSerializer());
        builder.registerTypeAdapter(Ability.class, new AbilitySerializer());
        //builder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        builder.registerTypeAdapter(Message.class, new MessageSerializer());
        builder.registerTypeAdapter(MoveType.class, new MoveTypeSerializer());
        builder.registerTypeAdapter(Dashboard.class, new DashBoardSerializer());
        builder.registerTypeAdapter(Performable.class, new PerformableSerializer());
        builder.registerTypeAdapter(DevelopmentCard.class, new DevCardExtSerializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardExtSerializer());
        builder.registerTypeAdapter(Requirements.class, new RequirementsExtSerializer());
        builder.registerTypeAdapter(ProductionPower.class, new ProductionPowerExtSerializer());
        builder.registerTypeAdapter(SetUp.class, new SetupperSerializer());
        builder.registerTypeAdapter(Settable.class, new SettableSerializer());

        builder.registerTypeAdapter(SoloActionTokens.class, (JsonSerializer<SoloActionTokens>) (soloActionTokens, type, context) -> soloActionTokens==null ? context.serialize(null) :context.serialize(TokenType.valueOf(soloActionTokens.getName()), TokenType.class));

        gson=builder.create();
        filePath = new File("").getAbsolutePath();
    }

    /*Methods to Initialize the Game*/
    public static ArrayList<LeaderCardExt> LeaderCardsParser() throws FileNotFoundException {
        Type LeaderListType = new TypeToken<ArrayList<LeaderCardExt>>(){}.getType();
        ArrayList<LeaderCardExt> leaderCardExts= gson.fromJson(new FileReader(filePath + "/src/main/resources/LeaderCards.json"), LeaderListType);
        for(LeaderCardExt card: leaderCardExts){
            card.setStatus(LeaderStatus.onHand);
        }
        return leaderCardExts;
    }

    public static ArrayList<SoloActionTokens> TokensParser() throws FileNotFoundException{
        Type TokensListType = new TypeToken<ArrayList<SoloActionTokens>>(){}.getType();
        GsonBuilder localBuilder = new GsonBuilder();
        localBuilder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        return localBuilder.create().fromJson(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"), TokensListType);
    }

    public static ArrayList<MarbleColor> MarblesParser() throws FileNotFoundException {
        Type marblesarray= new TypeToken<ArrayList<MarbleColor>>(){}.getType();
        return gson.fromJson(new FileReader(filePath+"/src/main/resources/Marbles.json"), marblesarray);
    }

    public static ArrayList<DevelopmentCardExt> DevCardParser() throws FileNotFoundException {
        Type devCardListType = new TypeToken<ArrayList<DevelopmentCardExt>>(){}.getType();
        return gson.fromJson(new FileReader(filePath + "/src/main/resources/DevCard.json"), devCardListType);
    }

    /*Methods for CanPerform*/
    public static boolean CanParseMarbles() {
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
        Type devCardListType = new TypeToken<ArrayList<DevelopmentCardExt>>(){}.getType();
        ArrayList<DevelopmentCardExt> developmentCards=new ArrayList<>();

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
        Type LeaderListType = new TypeToken<ArrayList<LeaderCardExt>>(){}.getType();
        ArrayList<LeaderCardExt> leaderCards=new ArrayList<>();

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

    /*Utilities*/

    public static String toJson(Object src, Type typeOfSrc){
        return gson.toJson(src, typeOfSrc);
    }

    public static <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    public static  <T> T fromJson(String json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }

}
