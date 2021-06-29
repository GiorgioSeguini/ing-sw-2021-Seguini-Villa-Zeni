package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.PersonalBoard;
import it.polimi.ingsw.constant.model.Player;

import java.util.ArrayList;

/**
 * MarketMessage class.
 * Implements Message interface.
 * Manage the market messages.
 */
public class PersonalBoardMessage implements Message{

    public static final String className = "PersonalBoardMessage";
    private final PersonalBoard personalBoard;
    private final int IDplayer;

    /**
     * Instantiates a new Personal board message.
     *
     * @param board of type PersonalBoard: the board.
     * @param IDplayer of type int: the player's ID.
     */
    public PersonalBoardMessage(PersonalBoard board, int IDplayer) {
        this.personalBoard = board;
        this.IDplayer = IDplayer;
    }

    /**
     * Handle the personal board messages.
     * @param client of type Client: reference to the client.
     */
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

    /**
     *
     * @return of type String: the class name, useful for json serialization.
     */
    @Override
    public String getName() {
        return className;
    }
}
