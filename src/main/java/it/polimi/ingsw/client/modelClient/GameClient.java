package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;

import java.util.ArrayList;

/**
 * Superclass that manage the all game, extends abstract class Game
 * Add some shortcuts methods to keep code more readable and some attribute
 * Override methods of other client class of the game in order to avoid cast
 */
public class GameClient extends Game {

    //player related attribute
    private transient int myID;
    private transient ArrayList<LeaderCardClient> leaderCards;


    /**
     * @return true if is my turn, false otherwise
     */
    public boolean isMyTurn(){
        if(getCurrPlayer()==null)
            return false;
        return getCurrPlayer().getID()==this.myID;
    }


    /**
     * Get a Player reference to client player
     *
     * @return me of type Player
     */
    public Player getMe(){
        return super.getPlayerFromID(myID);
    }

    /**
     * Gets id of client player
     *
     * @return the my id of type int
     */
//getter
    public int getMyID() {
        return myID;
    }

    /**
     * Gets a list of the possible choice for initial leader card.
     * Once leader card has been chose do not call this methods but refer to getMe().getPersonalBoard().getLeaderCards()
     *
     * @return an ArrayList of LeaderCardClient - the possible initial choices
     */
    public ArrayList<LeaderCardClient> getLeaderCards() {
        return leaderCards;
    }

    /**
     * @see Game#getSoloGame()
     * @return solo game - of type LorenzoSoloPlayerClient
     */
    @Override
    public LorenzoSoloPlayerClient getSoloGame() {
        return (LorenzoSoloPlayerClient) super.getSoloGame();
    }

    /**
     * @see Game#getDashboard()
     * @return dashboard - of type DashBoardClient
     */
    @Override
    public DashBoardClient getDashboard(){
        return (DashBoardClient) super.getDashboard();
    }

    //setter

    /**
     * Sets my id.
     * this methods is called by the Initial message
     * @see it.polimi.ingsw.constant.message.InitialMessage#handleMessage(Client)
     * @param myID of type int - my id
     */
    public void setMyID(int myID) {
        this.myID = myID;
    }


    /**
     * Set possible choices of initial leader cards.
     * this methods is called by the Initial message
     * @see it.polimi.ingsw.constant.message.InitialMessage#handleMessage(Client)
     * @param leaderCards the leader cards
     */
    public void setLeaderCards(ArrayList<LeaderCard> leaderCards){
        ArrayList<LeaderCardClient> temp = new ArrayList<>();
        for(LeaderCard c : leaderCards){
            temp.add((LeaderCardClient) c);
        }
        this.leaderCards= temp;
    }


    @Override
    public String toString(){
        String game = "Players: ";
        for(Player p: getPlayers()) {
            game += "\t"+p.getUserName();
        }
        game += "\n";
        game += "Game status: "+this.getStatus()+"\n";
        if(this.getCurrPlayer()==null){
            game += "Current player: game isn't start yet\n";
        }else{
            game += "Current player: "+this.getCurrPlayer().getUserName()+"\n";
        }
        game += "\n";
        return game;
    }
}
