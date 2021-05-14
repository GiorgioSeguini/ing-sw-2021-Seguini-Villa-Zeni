package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.LeaderCard;
import it.polimi.ingsw.client.modelClient.PersonalBoard;

import java.util.ArrayList;
import java.util.Scanner;


public class MoveChoseLeaderCards extends MoveType {

    private ArrayList<Integer> indexLeaderCards;
    public static final String className= "MoveChoseLeaderCards";

    public MoveChoseLeaderCards(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setIndexLeaderCards(ArrayList<Integer> indexLeaderCards) {
        this.indexLeaderCards = new ArrayList<>();
        this.indexLeaderCards.addAll(indexLeaderCards);
    }

    @Override
    public boolean canPerform(Game game) {
        if(!initialMove(game))
            return false;
        return !game.getPlayerFromID(game.getMyID()).getPersonalBoard().isReady();
    }

    @Override
    public void updateCLI(Game game, Scanner in){
        System.out.println("Scegli "+ PersonalBoard.MAX_LEAD_CARD + " carte leader tra le seguenti:");
        for(LeaderCard card : game.getLeaderCards()){
            System.out.println(card);
        }
        ArrayList<Integer> choice = new ArrayList<>();
        while(choice.size() < PersonalBoard.MAX_LEAD_CARD){
            int index = in.nextInt();
            if(index>0 && index<game.getLeaderCards().size() && !choice.contains(index)){
                choice.add(game.getLeaderCards().get(index).getId());
            }else{
                System.out.println("Indice non valido");
            }
        }
        this.setIndexLeaderCards(choice);
    }
}
