package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public interface cliInterface {

    public MoveType updateCLI(GameClient game, Scanner stdin);

    public boolean canPerform(GameClient game);

    String getName();

}

    /*@Override
    public void updateCLI(Game game, Scanner in) {
        System.out.println("Scegli le risorse iniziali:");
        NumberOfResources resources= new NumberOfResources();

        for(ResourceType type : ResourceType.values()){
            System.out.println(type);
        }
        while(resources.size()< game.getInitialResources()){
            System.out.println("devi scegliere " + (game.getInitialResources() - resources.size()) + " risorse");
            int index = in.nextInt() -1;
            if(index>=0 && index < ResourceType.values().length){
                resources = resources.add(ResourceType.values()[index], 1);
            }else{
                System.out.println("invalid index");
            }
        }
        this.setResources(resources);
        System.out.println("ottima scelta");
    }*/

    /*@Override
    public void updateCLI(Game game, Scanner in){
        System.out.println("Scegli "+ PersonalBoard.MAX_LEAD_CARD + " carte leader tra le seguenti:");
        for(LeaderCard card : game.getLeaderCards()){
            System.out.println(card);
        }
        ArrayList<Integer> choice = new ArrayList<>();
        while(choice.size() < PersonalBoard.MAX_LEAD_CARD){
            int index = in.nextInt();
            if(index>0 && index<game.getLeaderCards().size() && !choice.contains(index)){
                choice.add(game.getLeaderCards().get(index).getId());
            }else{
                System.out.println("Indice non valido");
            }
        }
        this.setIndexLeaderCards(choice);
    }*/

    /*@Override
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
    }*/