package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.model.LeaderCard;
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
        int m=0;
        do{
            System.out.println("Cosa vuoi fare?\n\t1. Scartare una carta leader.\n\t2. Attivare l'abilità di una carta leader.");
            m = stdin.nextInt();
        }while(m!=1 && m!=2);

        if(m==1){
            System.out.println("Che carta leader vuoi scartare?\n");
            move.setMove(1);
        }
        if (m==2){
            System.out.println("Che carta leader vuoi attivare?\n");
            move.setMove(0);
        }
        //TODO da discutere

        int i=1;
        for(LeaderCard l:  game.getMe().getPersonalBoard().getLeaderCards()){
            System.out.println(""+i+".\n");
            System.out.println("\t"+l);
            i++;
        }

        int numcard = 0;
        do{
            numcard=stdin.nextInt();
        }while(numcard<1 || numcard>(i-1));
        move.setIdLeaderCard(game.getLeaderCards().get(numcard-1).getId());

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
