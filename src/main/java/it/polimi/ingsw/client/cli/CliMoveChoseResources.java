package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.PersonalBoard;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.constant.move.MoveType;

import javax.swing.*;
import java.util.Scanner;

public class CliMoveChoseResources implements CliInterface{

    private MoveChoseResources move;

    public CliMoveChoseResources(int myId){
        this.move = new MoveChoseResources(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        NumberOfResources numberOfResources = new NumberOfResources();
        System.out.println("Risorse attuali totali: "+ game.getMe().getDepots().getResources());
        if(game.getMe().getToActive().getOfYourChoiceInput()==1){
            System.out.println("Scegli la risorsa in input!");
            printRes();
            int res = CLI.ReadFromKeyboard(stdin);
            move.setOfYourChoiceInput(numberOfResources.add(ResourceType.values()[res-1],1));
        }else{
            System.out.println("Scegli le risorse in input!");
            printRes();
            while(numberOfResources.size()< game.getMe().getToActive().getOfYourChoiceInput()){
                int res = CLI.ReadFromKeyboard(stdin);
                if(res>0 && res<=ResourceType.values().length){
                    numberOfResources=numberOfResources.add(ResourceType.values()[res-1],1);

                }else{
                    System.out.println("Indice non valido");
                }
            }
            move.setOfYourChoiceInput(numberOfResources);
        }
        System.out.println("Scegli la risorsa in output!");
        printRes();
        int res = CLI.ReadFromKeyboard(stdin);
        move.setOfYourChoiceOutput(new NumberOfResources().add(ResourceType.values()[res-1],1));
        return move;
        // TODO: 5/21/21 cambiare (ricordare che la produzione è comulativa, printare howmany productions)  
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
