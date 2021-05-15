package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public interface cliInterface {

    public MoveType updateCLI(Game game, Scanner stdin);

    public boolean canPerform(Game game);

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