package it.polimi.ingsw.constant.message;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.*;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
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

        if(client.getSimpleGame().getMyID()!=IDplayer){
            ArrayList<LeaderCard> cards = new ArrayList<>();
            for(LeaderCard card : personalBoard.getLeaderCards()){
                if(card.getStatus()== LeaderStatus.Played)
                    cards.add(card);
            }
            personalBoard.setLeaderCards(cards);
        }

        owner.setPersonalBoard(personalBoard);
    }

    @Override
    public String getName() {
        return className;
    }
}
