package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.PersonalBoard;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliMoveChoseResources implements CliInterface{

    private MoveChoseResources move;

    public CliMoveChoseResources(int myId){
        this.move = new MoveChoseResources(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        NumberOfResources numberOfResources = new NumberOfResources();
        if(game.getMe().getToActive().getOfYourChoiceInput()==1){
            System.out.println("Scegli la risorsa in input!");
            printRes();
            int res = stdin.nextInt();
            move.setOfYourChoiceInput(numberOfResources.add(ResourceType.values()[res-1],1));
        }else{
            System.out.println("Scegli le risorse in input!");
            printRes();
            while(numberOfResources.size()==game.getMe().getToActive().getOfYourChoiceInput()){
                int res = stdin.nextInt();
                if(res>0 && res<=ResourceType.values().length){
                    numberOfResources.add(ResourceType.values()[res-1],1);

                }else{
                    System.out.println("Indice non valido");
                }
            }
            move.setOfYourChoiceInput(numberOfResources);
        }
        System.out.println("Scegli la risorsa in output!");
        printRes();
        int res = stdin.nextInt();
        move.setOfYourChoiceOutput(numberOfResources.add(ResourceType.values()[res-1],1));
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

    private void printRes(){
        int i=1;
        for(ResourceType type : ResourceType.values()){
            System.out.println(i+". "+type);
            i++;
        }
    }
}
