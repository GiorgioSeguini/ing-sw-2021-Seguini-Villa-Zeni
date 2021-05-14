package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.client.move.MoveChoseLeaderCards;
import it.polimi.ingsw.client.move.MoveType;

import java.util.Scanner;

public class cliChoseLeaderCard implements cliInterface{

    private MoveChoseLeaderCards move;

    public cliChoseLeaderCard(int myId) {
        this.move = new MoveChoseLeaderCards(myId);
    }

    @Override
    public MoveType updateCLI(Game game, Scanner stdin) {
        //TODO
        return move;
    }

    @Override
    public boolean canPerform(Game game) {
        return move.canPerform(game);
    }

    @Override
    public String getName() {
        return move.getClassName();
    }
}
