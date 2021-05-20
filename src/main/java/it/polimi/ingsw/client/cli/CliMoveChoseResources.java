package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliMoveChoseResources implements CliInterface{

    private MoveChoseResources move;

    public CliMoveChoseResources(int myId){
        this.move = new MoveChoseResources(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        System.out.println("Scegli la risorsa!");


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
