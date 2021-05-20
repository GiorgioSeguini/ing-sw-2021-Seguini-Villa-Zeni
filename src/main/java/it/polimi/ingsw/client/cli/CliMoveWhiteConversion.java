package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.move.MoveWhiteConversion;

import java.util.Scanner;

public class CliMoveWhiteConversion implements CliInterface{

    private MoveWhiteConversion move;

    public CliMoveWhiteConversion(int myId){
        this.move = new MoveWhiteConversion(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        System.out.println("Come vuoi convertire le biglie bianche?");

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
