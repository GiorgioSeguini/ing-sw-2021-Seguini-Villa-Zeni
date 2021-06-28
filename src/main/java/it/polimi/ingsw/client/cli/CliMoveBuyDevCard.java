package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import it.polimi.ingsw.constant.move.MoveType;

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
            System.out.println("Risorse attuali totali: "+ game.getMe().getDepots().getResources());
            int l;
            do {
                System.out.println("Scegli il livello della carta sviluppo che vuoi comprare!\n");
                System.out.println("\t1. ONE\n\t2. TWO\n\t3. THREE");
                l = CLI.ReadFromKeyboard(stdin);
                if(l<=0 || l>3) System.out.println("Invalid Choise!");
            }while (l<=0||l>3);

            int c;
            do {
                System.out.println("Scegli il colore della carta sviluppo che vuoi comprare!\n");
                System.out.println("\t1. YELLOW\n\t2. GREEN\n\t3. BLUE\n\t4. PURPLE");
                c = CLI.ReadFromKeyboard(stdin);
                if(c<=0 || c>4) System.out.println("Invalid Choise!");
            }while (c<=0||c>4);
            System.out.println("Vuoi comprare questa carta sviluppo?\n\t1. YES \t2. NO\n"+game.getDashboard().getTopDevCard(ColorDevCard.values()[c-1],Level.values()[l-1]));
            check = CLI.ReadFromKeyboard(stdin);
            index = game.getDashboard().getTopDevCard(ColorDevCard.values()[c-1],Level.values()[l-1]).getId();
        }while(check !=1);
        move.setIndexCardToBuy(index);
        int pos;
        do{
            System.out.println("In che posizione vuoi mettere la carta appena comprata?\n");
            System.out.println("\t1. Slot 1 della PersonalBoard\n\t2. Slot 2 della PersonalBoard\n\t3. Slot 3 della PersonalBoard\n");
            pos = CLI.ReadFromKeyboard(stdin);
            if(pos<1||pos>3) System.out.println("Invalid Choise!");
        }while(pos<1||pos>3);
        move.setPos(pos-1);
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
