package it.polimi.ingsw.client.modelClient;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;

import java.util.ArrayList;

public class GameClient extends Game {

    //player related attribute
    private transient int myID;
    private transient ArrayList<LeaderCardClient> leaderCards;


    public boolean isMyTurn(){
        if(getCurrPlayer()==null)
            return false;
        return getCurrPlayer().getID()==this.myID;
    }


    public Player getMe(){
        return super.getPlayerFromID(myID);
    }

    //getter
    public int getMyID() {
        return myID;
    }

    public ArrayList<LeaderCardClient> getLeaderCards() {
        return leaderCards;
    }

    @Override
    public LorenzoSoloPlayerClient getSoloGame() {
        return (LorenzoSoloPlayerClient) super.getSoloGame();
    }

    //setter
    public void setMyID(int myID) {
        this.myID = myID;
    }


    public void setLeaderCards(ArrayList<LeaderCard> leaderCards){
        ArrayList<LeaderCardClient> temp = new ArrayList<>();
        for(LeaderCard c : leaderCards){
            temp.add((LeaderCardClient) c);
        }
        this.leaderCards= temp;
    }

    @Override
    public DashBoardClient getDashboard(){
        return (DashBoardClient) super.getDashboard();
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
