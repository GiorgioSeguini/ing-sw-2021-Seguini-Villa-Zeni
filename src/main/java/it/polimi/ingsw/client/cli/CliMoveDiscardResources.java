package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveDiscardResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliMoveDiscardResources implements CliInterface{

    private MoveDiscardResources move;

    public CliMoveDiscardResources(int myId){
        this.move = new MoveDiscardResources(myId);
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
