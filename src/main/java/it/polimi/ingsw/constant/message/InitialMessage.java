package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;

import java.util.ArrayList;

/**
 * InitialMessage class.
 * Implements Message interface.
 * Manage initial game messages.
 */
public class InitialMessage implements Message{

    public static final String className = "InitialMessage";

    private final Game model;
    private final int yourID;
    private final ArrayList<LeaderCard> leaderCards;
    private final String roomName;

    /**
     * Instantiates a new Initial message.
     *
     * @param model of type Game: the game's model.
     * @param yourID of type int: your id.
     * @param leaderCards of type ArrayList<LeaderCard>: the leader cards.
     * @param roomName of type String: the room name.
     */
    public InitialMessage(Game model, int yourID, ArrayList<LeaderCard> leaderCards, String roomName) {
        this.model = model;
        this.yourID = yourID;
        this.leaderCards = leaderCards;
        this.roomName= roomName;
    }

    /**
     * Handle initial messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client){
        client.setSimpleGame((GameClient) model);
        GameClient simpleGame = client.getSimpleGame();
        simpleGame.setMyID(yourID);
        client.setRoomName(roomName);
        simpleGame.setLeaderCards(leaderCards);
        for(Player p : simpleGame.getPlayers()){
            if(p.getID()!=yourID){
                ArrayList<LeaderCard> cards = new ArrayList<>();
                if(p.getPersonalBoard().getLeaderCards()  != null) {
                    for (LeaderCard card : p.getPersonalBoard().getLeaderCards()) {
                        if (card.getStatus() == LeaderStatus.Played)
                            cards.add(card);
                    }
                }
                p.getPersonalBoard().setLeaderCards(cards);
            }
        }
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
