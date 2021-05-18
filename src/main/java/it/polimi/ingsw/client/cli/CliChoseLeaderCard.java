package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.PersonalBoard;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.ArrayList;
import java.util.Scanner;

public class CliChoseLeaderCard implements CliInterface {

    private final MoveChoseLeaderCards move;

    public CliChoseLeaderCard(int myId) {
        this.move = new MoveChoseLeaderCards(myId);
    }


    @Override
    public MoveType updateCLI(GameClient game, Scanner in){
        System.out.println("Scegli "+ PersonalBoard.MAX_LEAD_CARD + " carte leader tra le seguenti:");
        int t=1;
        for(LeaderCard card : game.getLeaderCards()){
            System.out.println(""+t+".\n"+card);
            t++;
        }
        ArrayList<Integer> choice = new ArrayList<>();
        int preindex=0;
        while(choice.size() < PersonalBoard.MAX_LEAD_CARD){
            int index = in.nextInt();
            if(index>0 && index<=game.getLeaderCards().size() && !choice.contains(index) && preindex!=index){
                choice.add(game.getLeaderCards().get(index-1).getId());
                preindex = index;
            }else{
                System.out.println("Indice non valido");
            }
        }
        move.setIndexLeaderCards(choice);
        return move;
    }

    @Override
    public boolean canPerform(GameClient game) {
        return move.canPerform(game);
    }

    @Override
    public String getName() {
        return move.getClassName();
    }
}
