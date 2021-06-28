package it.polimi.ingsw.server.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.modelClient.TokenType;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.*;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.parse.MessageSerializer;
import it.polimi.ingsw.constant.parse.MoveTypeSerializer;
import it.polimi.ingsw.constant.parse.NumberOfResSerializer;
import it.polimi.ingsw.constant.parse.SetupperSerializer;
import it.polimi.ingsw.constant.setupper.SetUp;
import it.polimi.ingsw.server.controller.Performable;
import it.polimi.ingsw.server.model.Ability;
import it.polimi.ingsw.server.model.DevelopmentCardExt;
import it.polimi.ingsw.server.model.LeaderCardExt;
import it.polimi.ingsw.server.model.SoloActionTokens;
import it.polimi.ingsw.server.network.Settable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Starter class.
 * Class that Initialize the Game with the corrects files.
 */
@SuppressWarnings("ALL")
public class Starter {

    private static final int NUMOFMARBELS=13;
    private static final int NUMOFDEVCARD=48;
    private static final int NUMOFLEADERCARD=16;
    private static final int NUMOFTOKENS=7;

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

        builder.registerTypeAdapter(SoloActionTokens.class, (JsonSerializer<SoloActionTokens>) (soloActionTokens, type, context) -> soloActionTokens==null ? context.serialize(null) :context.serialize(TokenType.valueOf(soloActionTokens.getEnum()), TokenType.class));

        gson=builder.create();
        filePath = new File("").getAbsolutePath();
    }

    /**
     * Leader cards parser.
     *
     * @return of type ArrayList<LeaderCardExt>: the leader card's array list.
     */
    /*Methods to Initialize the Game*/
    public static ArrayList<LeaderCardExt> LeaderCardsParser() {
        Type LeaderListType = new TypeToken<ArrayList<LeaderCardExt>>(){}.getType();
        ArrayList<LeaderCardExt> leaderCardExts= gson.fromJson(new InputStreamReader(Objects.requireNonNull(Starter.class.getResourceAsStream("/LeaderCards.json"))), LeaderListType);
        for(LeaderCardExt card: leaderCardExts){
            card.setStatus(LeaderStatus.onHand);
        }
        return leaderCardExts;
    }

    /**
     * Tokens parser.
     *
     * @return of type ArrayList<SoloActionTokens>: the SoloActionTokens's array list.
     */
    public static ArrayList<SoloActionTokens> TokensParser() {
        Type TokensListType = new TypeToken<ArrayList<SoloActionTokens>>(){}.getType();
        GsonBuilder localBuilder = new GsonBuilder();
        localBuilder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
        return localBuilder.create().fromJson(new InputStreamReader(Objects.requireNonNull(Starter.class.getResourceAsStream("/SoloActionTokens.json"))), TokensListType);
    }

    /**
     * Marbles parser.
     *
     * @return of type ArrayList<MarbleColor>: the marble's array list.
     */
    public static ArrayList<MarbleColor> MarblesParser() {
        Type marblesarray= new TypeToken<ArrayList<MarbleColor>>(){}.getType();
        return gson.fromJson(new InputStreamReader(Objects.requireNonNull(Starter.class.getResourceAsStream("/Marbles.json"))), marblesarray);
    }

    /**
     * Development card parser.
     *
     * @return of type ArrayList<DevelopmentCardExt>: the development card's array list.
     */
    public static ArrayList<DevelopmentCardExt> DevCardParser() {
        Type devCardListType = new TypeToken<ArrayList<DevelopmentCardExt>>(){}.getType();
        return gson.fromJson(new InputStreamReader(Objects.requireNonNull(Starter.class.getResourceAsStream("/DevCard.json"))), devCardListType);
    }

    /**
     * Check if it can parse marbles.
     *
     * @return the boolean
     */
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

    /**
     * Check if it can parse development cards.
     *
     * @return the boolean
     */
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

    /**
     * Check if it can parse leader cards.
     *
     * @return the boolean
     */
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

    /**
     *Check if it can parse tokens.
     *
     * @return the boolean
     */
    public static boolean CanTokensParser(){
        Type TokensListType = new TypeToken<ArrayList<SoloActionTokens>>(){}.getType();
        ArrayList<SoloActionTokens> tokens= new ArrayList<>();

        try {
            GsonBuilder localBuilder = new GsonBuilder();
            localBuilder.registerTypeAdapter(SoloActionTokens.class, new TokensSerializer());
            tokens = localBuilder.create().fromJson(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"), TokensListType);
            //tokens=gson.fromJson(new FileReader(filePath + "/src/main/resources/SoloActionTokens.json"), TokensListType);
        } catch (FileNotFoundException e) {
            return false;
        }

        if(tokens.size()!=NUMOFTOKENS){
            return false;
        }

        return true;
    }

    /*Utilities*/

    /**
     * To json string.
     *
     * @param src       the src
     * @param typeOfSrc the type of src
     * @return the string
     */
    public static String toJson(Object src, Type typeOfSrc){
        return gson.toJson(src, typeOfSrc);
    }

    /**
     * From json t.
     *
     * @param <T>      the type parameter
     * @param json     the json
     * @param classOfT the class of t
     * @return the t
     */
    public static <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }

    /**
     * From json t.
     *
     * @param <T>     the type parameter
     * @param json    the json
     * @param typeOfT the type of t
     * @return the t
     */
    public static  <T> T fromJson(String json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }

}
