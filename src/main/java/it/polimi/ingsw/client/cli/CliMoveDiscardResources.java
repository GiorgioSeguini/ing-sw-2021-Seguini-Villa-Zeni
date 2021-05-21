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
        NumberOfResources numOfRes= new NumberOfResources();

        System.out.println("Risorse da posizionare:\n"+game.getMe().getConverter().getResources());
        System.out.println("Risorse nel WareHouseDepots: \n"+ game.getMe().getDepots().getWareHouseDepots().getResources());
        System.out.println("Quante risorse vuoi scartare?\n\t(inserisci 0 per non scartare nessuna quantità di quel tipo di risorsa).");
        for(ResourceType resourceType: ResourceType.values()){
            int num;
            do{
                System.out.println(resourceType+":");
                num = stdin.nextInt();
                if(num>game.getMe().getConverter().getResources().getAmountOf(resourceType) || num<0){
                    System.out.println("Invalid index!");
                }
                else{
                    numOfRes=numOfRes.add(resourceType,num);
                }
            }while(num>game.getMe().getConverter().getResources().getAmountOf(resourceType) || num<0);
        }
        move.setToDiscard(numOfRes);
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
