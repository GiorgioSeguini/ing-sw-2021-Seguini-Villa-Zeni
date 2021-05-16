package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class cliChoseLeaderCard implements cliInterface{

    private MoveChoseLeaderCards move;

    public cliChoseLeaderCard(int myId) {
        this.move = new MoveChoseLeaderCards(myId);
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
