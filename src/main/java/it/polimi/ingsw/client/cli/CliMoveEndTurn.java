package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

/**
 * CliMoveEndTurn class.
 * Implements CliInterface.
 * Manage the end turn move on the cli.
 */
public class CliMoveEndTurn implements CliInterface{

    private final MoveEndTurn move;

    /**
     * Instantiates a new Cli move end turn.
     *
     * @param myId of type int: the player's id.
     */
    public CliMoveEndTurn(int myId){
        this.move = new MoveEndTurn(myId);
    }

    /**
     * Update cli.
     * @param game of type GameClient: the game.
     * @param stdin of type Scanner:  the input scanner.
     * @return of type MoveType: the move.
     */
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        System.out.println("Hai deciso di terminare il turno. Premi INVIO per far continuare il gioco.");
        stdin.nextLine();
        return  move;
    }

    /**
     * @see MoveType#canPerform(Game)
     */
    @Override
    public boolean canPerform(GameClient game) {
        return move.canPerform(game);
    }

    /**
     *
     * @see MoveType#getClassName()
     */
    @Override
    public String getName() {
        return move.getClassName();
    }
}
