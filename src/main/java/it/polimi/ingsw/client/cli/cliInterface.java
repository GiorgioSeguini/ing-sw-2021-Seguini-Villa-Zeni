package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.client.move.MoveType;

import java.util.Scanner;

public interface cliInterface {

    public MoveType updateCLI(Game game, Scanner stdin);

    public boolean canPerform(Game game);

    String getName();

}
