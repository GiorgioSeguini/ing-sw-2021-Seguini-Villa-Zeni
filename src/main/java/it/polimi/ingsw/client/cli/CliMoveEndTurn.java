package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliMoveEndTurn implements CliInterface{

    private MoveEndTurn move;

    public CliMoveEndTurn(int myId){
        this.move = new MoveEndTurn(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        int c;
        do {
            System.out.println("Vuoi terminare il tuo turno?\n\t1. YES\t2. NO");
            c = stdin.nextInt();
            if (c != 1 && c != 2) {
                System.out.println("Invalid index!");
            } else {
                if (c == 1) {
                    return move;
                } else {
                    break;
                }
            }
        }while(false);
        return  move;
        //TODO da discutere
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
