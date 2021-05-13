package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

import java.util.Scanner;

public class MovetypeMarket extends MoveType {

    private int indexToBuy;
    public static final String className = "MovetypeMarket";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    public MovetypeMarket(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setIndexToBuy(int indexToBuy) {
        this.indexToBuy = indexToBuy;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }

    @Override
    public void updateCLI(Game game, Scanner in) {
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
        this.setIndexToBuy(index);
    }
}
        /*System.out.println("Inserisci l'indice desiderato");
        int temp = in.nextInt();
        if(temp<0 || temp>7){
            System.out.println("Invalid choice");
        }
        index+=temp;
        this.setIndexToBuy(index);*/


