package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.move.MoveLeader;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliMoveLeader implements CliInterface{

    private MoveLeader move;

    public CliMoveLeader(int myId){
        this.move = new MoveLeader(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        /*int m;
        do{
            System.out.println("Cosa vuoi fare?\n\t1. Scartare una carta leader.\n\t2. Attivare l'abilità di una carta leader.");
            m = stdin.nextInt();
        }while(m!=1 && m!=2);
        if(m==1){
            System.out.println("Che carta leader vuoi scartare?\n");
            int i=1;
            for(LeaderCardClient l: (LeaderCardClient[]) game.getMe().getPersonalBoard().getLeaderCards()){
                System.out.println(""+i+".\n");
                System.out.println("\t"+l);
                i++;
            }
            int numcard = stdin.nextInt();
            move.setIdLeaderCard(game.getLeaderCards().get(numcard).getId());
        }
        if (m==2){
            System.out.println("Che carta leader vuoi attivare?\n"+game.getLeaderCards());
            for(LeaderCardClient leadercard: (LeaderCardClient[]) game.getMe().getPersonalBoard().getLeaderCards()){
                int i=1;
                System.out.println("\t"+i+".");
                System.out.println(""+leadercard);
                i++;
            }
            int numcard = stdin.nextInt();
            move.setIdLeaderCard(game.getLeaderCards().get(numcard).getId());
        }*/
        //TODO da discutere

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
