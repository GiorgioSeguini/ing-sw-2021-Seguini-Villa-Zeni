package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

/**
 * The interface CliInterface.
 */
public interface CliInterface {

    /**
     * Update cli.
     *
     * @param game of type GameClient: the game.
     * @param stdin of type Scanner: the stdin.
     * @return of type MoveType: the move type done.
     */
    MoveType updateCLI(GameClient game, Scanner stdin);

    /**
     * Can perform check.
     *
     * @param game of type GameClient: the game.
     * @return of type boolean: True if it can perform. Otherwise False.
     */
    boolean canPerform(GameClient game);

    /**
     * Gets name.
     *
     * @return of type String: the name.
     */
    String getName();

}