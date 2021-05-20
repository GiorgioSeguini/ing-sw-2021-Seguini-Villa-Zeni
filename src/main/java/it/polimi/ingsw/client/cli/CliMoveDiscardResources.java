package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveDiscardResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliMoveDiscardResources implements CliInterface{

    private MoveDiscardResources move;

    public CliMoveDiscardResources(int myId){
        this.move = new MoveDiscardResources(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        int servants;
        int shield;
        int coins;
        int stones;
        int[] numofres= new int[4];
        int i=0;
        NumberOfResources totalres=game.getMe().getConverter().getResources().add(game.getMe().getDepots().getWareHouseDepots().getResources());
        System.out.println("Risorse da posizionare:\n"+totalres);
        System.out.println("Quante risorse vuoi scartare?\n\t(inserisci 0 per non scartare nessuna quantità di quel tipo di risorsa).");
        for(ResourceType resourceType: ResourceType.values()){
            int num;
            do{
                System.out.println(resourceType+":");
                num = stdin.nextInt();
                if(num>game.getMe().getConverter().getResources().getAmountOf(resourceType)||num<0){
                    System.out.println("Invalid index!");
                }
            }while(num>game.getMe().getConverter().getResources().getAmountOf(resourceType)&&num<0);
            numofres[i]=num;
            i++;
        }
        servants=numofres[0];
        shield=numofres[1];
        coins=numofres[2];
        stones=numofres[3];
        move.setToDiscard(new NumberOfResources(servants,shield,coins,stones));
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
