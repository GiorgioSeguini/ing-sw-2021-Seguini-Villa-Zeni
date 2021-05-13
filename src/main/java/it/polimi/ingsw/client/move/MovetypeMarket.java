package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

import java.util.Scanner;

public class MovetypeMarket extends MoveType {

    private int indexToBuy;
    public static final String className= "MovetypeMarket";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    public MovetypeMarket(int idPlayer){
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
        System.out.println("Vuoi comprare una riga o una colonna?");
        int index = 0;
        String c = in.nextLine();
        switch(c) {
            case "r":
            case "riga":
                index = 0;
                break;
            case "c":
            case "colonna":
                index = 4;
                break;
            default:
                System.out.println("Invalid choice");
        }

        System.out.println("Inserisci l'indice desiderato");
        int temp = in.nextInt();
        if(temp<0 || temp>7){
            System.out.println("Invalid choice");
        }
        index+=temp;
        this.setIndexToBuy(index);
    }
}
