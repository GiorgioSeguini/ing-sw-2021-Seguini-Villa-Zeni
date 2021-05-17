package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.move.MoveTypeMarket;

import java.util.Scanner;

public class cliMarketMove implements cliInterface{

    private MoveTypeMarket move;

    public cliMarketMove(int myId) {
        this.move = new MoveTypeMarket(myId);
    }

    @Override
    public MoveType updateCLI(GameClient game, Scanner in) {
        System.out.println(game.getMarketTray());
        System.out.println("Vuoi comprare una riga(1) o una colonna(2)?");
        int index = 0;
        int c = in.nextInt();
        if (c==1) {
            System.out.println("Inserisci l'indice della riga desiderata");
            int temp = in.nextInt();
            if (temp <= 0 || temp > 3) {
                System.out.println("Invalid choice");
            }
            index += temp+3;
        }

        if (c==2) {
            System.out.println("Inserisci l'indice della colonna desiderata");
            int temp = in.nextInt();
            if (temp <= 0 || temp > 4) {
                System.out.println("Invalid choice");
            }
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
