package it.polimi.ingsw.constant.message;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.*;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.model.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersonalBoardMessage implements Message{

    public static final String className = "PersonalBoardMessage";
    private final PersonalBoard personalBoard;
    private final int IDplayer;

    public PersonalBoardMessage(PersonalBoard board, int IDplayer) {
        this.personalBoard = board;
        this.IDplayer = IDplayer;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(IDplayer);
        owner.setPersonalBoard(personalBoard);
    }

    @Override
    public String getName() {
        return className;
    }
}
