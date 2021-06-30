package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.move.MoveTypeMarket;

import java.util.Scanner;

/**
 * CliMoveTypeMarket class.
 * Implements CliInterface.
 * Manage the market move on the cli.
 */
public class CliMoveTypeMarket implements CliInterface {

    private final MoveTypeMarket move;

    /**
     * Instantiates a new Cli move type market.
     *
     * @param myId of type int: the player's id.
     */
    public CliMoveTypeMarket(int myId) {
        this.move = new MoveTypeMarket(myId);
    }

    /**
     * Update cli.
     * @param game of type GameClient: the game.
     * @param in of type Scanner:  the input scanner.
     * @return of type MoveType: the move.
     */
    @Override
    public MoveType updateCLI(GameClient game, Scanner in) {
        System.out.println(game.getMarketTray());
        int c;
        if(game.getMe().getConverter().isWhiteAbilityActive()){
            System.out.println("La conversione delle biglie bianche è attiva.");
            System.out.print("Le biglie saranno convertite in: ");
            for(ResourceType x: game.getMe().getConverter().getToconvert()){
                System.out.print("\t"+x);
            }
            System.out.println();
        }

        do{
            System.out.println("Cosa vuoi fare?\n");
            System.out.println("1. Compra una riga.\n2. Compra una colonna.");
            c = CLI.ReadFromKeyboard(in);
        }while(c!=1 && c!=2);

        int index = 0;
        if (c==1) {
            int temp;
            do {
                System.out.println("Inserisci l'indice della riga desiderata");
                temp = CLI.ReadFromKeyboard(in);
                if (temp <= 0 || temp > 3) {
                    System.out.println("Invalid choice");
                }
            }while(temp<=0 || temp>3);
            index += temp + 3;
        }

        if (c==2) {
            int temp;
            do {
                System.out.println("Inserisci l'indice della colonna desiderata");
                temp = CLI.ReadFromKeyboard(in);
                if (temp <= 0 || temp > 4) {
                    System.out.println("Invalid choice");
                }
            }while(temp<=0 || temp>4);
            index += temp-1;
        }
        move.setIndexToBuy(index);
        return move;
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
