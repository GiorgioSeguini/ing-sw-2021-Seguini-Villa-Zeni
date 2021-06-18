package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.move.MoveType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public interface CliInterface {

    MoveType updateCLI(GameClient game, Scanner stdin);

    boolean canPerform(GameClient game);

    String getName();

}