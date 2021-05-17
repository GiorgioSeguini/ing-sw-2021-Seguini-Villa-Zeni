package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliMoveActiveProduction implements CliInterface{
    private MoveActiveProduction move;

    public CliMoveActiveProduction(int myId){
        this.move = new MoveActiveProduction(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        //TODO
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
