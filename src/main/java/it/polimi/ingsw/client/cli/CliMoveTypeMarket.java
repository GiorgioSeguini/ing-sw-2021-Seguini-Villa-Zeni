package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.move.MoveTypeMarket;

import java.util.Scanner;

public class CliMoveTypeMarket implements CliInterface {

    private MoveTypeMarket move;

    public CliMoveTypeMarket(int myId) {
        this.move = new MoveTypeMarket(myId);
    }

    @Override
    public MoveType updateCLI(GameClient game, Scanner in) {
        System.out.println(game.getMarketTray());
        int c;
        do{
            System.out.println("Cosa vuoi fare?\n");
            System.out.println("1. Compra una riga.\n2. Compra una colonna.");
            c = in.nextInt();
        }while(c!=1 && c!=2);

        int index = 0;
        if (c==1) {
            int temp;
            do {
                System.out.println("Inserisci l'indice della riga desiderata");
                temp = in.nextInt();
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
                temp = in.nextInt();
                if (temp <= 0 || temp > 4) {
                    System.out.println("Invalid choice");
                }
            }while(temp<=0 || temp>4);
            index += temp-1;
        }
        move.setIndexToBuy(index);
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
