package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.setupper.DisconnectConnectionSetupper;

import java.util.Scanner;

/**
 * CliDisconnect class.
 * Implements CliInterface.
 * Manage the disconnection on the cli.
 */
public class CliDisconnect implements CliInterface{

    private final Client client;

    /**
     * Instantiates a new Cli disconnect.
     *
     * @param client of type Client: the client.
     */
    public CliDisconnect(Client client){
        this.client= client;
    }

    /**
     * Update cli.
     * @param game of type GameClient: the game.
     * @param in of type Scanner:  the input scanner.
     * @return of type MoveType: the move.  Can return null.
     */
    @Override
    public MoveType updateCLI(GameClient game, Scanner in) {
        System.out.println("Vuoi per caso uscire dal gioco? ");
        int index = 0;
        do{
            System.out.println("\t 1. SI, voglio uscire dal gioco.");
            System.out.println("\t 2. NO.");
            index=CLI.ReadFromKeyboard(in);
            if(index<1||index>2) System.out.println("Indice non valido.");
        }while (index<1|| index>2);

        if (index==1){
            client.sendSetupper(new DisconnectConnectionSetupper(game.getMe().getUserName(), client.getRoomName()));
            return new MoveEndTurn(game.getMyID());
        }else return null;
    }

    /**
     *
     * @param game of type GameClient: the game.
     * @return of type boolean: always True
     */
    @Override
    public boolean canPerform(GameClient game) {
        return true;
    }

    /**
     *
     * @return of type String: close game
     */
    @Override
    public String getName() {
        return "Close Game";
    }
}
