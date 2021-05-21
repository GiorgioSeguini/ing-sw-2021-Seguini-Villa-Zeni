package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;

import java.util.Scanner;

public class CliMoveBuyDevCard implements CliInterface{

    private MoveBuyDevCard move;

    public CliMoveBuyDevCard(int myId){
        this.move = new MoveBuyDevCard(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        int check;
        int index;
        System.out.println(""+game.getDashboard());
        do {
            int l;
            do {
                System.out.println("Scegli il livello della carta sviluppo che vuoi comprare!\n");
                System.out.println("\t1. ONE\n\t2. TWO\n\t3. THREE");
                l = stdin.nextInt();
                if(l<=0 || l>3) System.out.println("Invalid Choise!");
            }while (l<=0||l>3);

            int c;
            do {
                System.out.println("Scegli il colore della carta sviluppo che vuoi comprare!\n");
                System.out.println("\t1. YELLOW\n\t2. GREEN\n\t3. BLUE\n\t4. PURPLE");
                c = stdin.nextInt();
                if(c<=0 || c>4) System.out.println("Invalid Choise!");
            }while (c<=0||c>4);
            System.out.println("Vuoi comprare questa carta sviluppo?\n\t1. YES \t2. NO\n"+game.getDashboard().getTopDevCard(ColorDevCard.values()[c-1],Level.values()[l-1]));
            check = stdin.nextInt();
            index = game.getDashboard().getTopDevCard(ColorDevCard.values()[c-1],Level.values()[l-1]).getId();
        }while(check == 2);
        move.setIndexCardToBuy(index);
        return move;
    }

    @Override
    public boolean canPerform(GameClient game) {
        if(move.canPerform(game)){
            return game.getDashboard().isSomethingBuyable(game);
        }
        return false;
    }

    @Override
    public String getName() {
        return move.getClassName();
    }
}
