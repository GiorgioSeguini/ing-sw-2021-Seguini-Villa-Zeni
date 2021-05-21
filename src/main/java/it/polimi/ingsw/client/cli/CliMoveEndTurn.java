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
        System.out.println("Hai deciso di terminare il turno. Premi INVIO per far continuare il gioco.");
        stdin.nextLine();
        String todiscard=stdin.nextLine();
        return  move;
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
