package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.move.MoveWhiteConversion;

import java.util.ArrayList;
import java.util.Scanner;

public class CliMoveWhiteConversion implements CliInterface{

    private MoveWhiteConversion move;

    public CliMoveWhiteConversion(int myId){
        this.move = new MoveWhiteConversion(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        System.out.println("Come vuoi convertire le biglie bianche?");
        ArrayList<ResourceType> resourceTypes = new ArrayList<>(game.getMe().getConverter().getWhite());
        int howMuchMarbles=game.getMe().getConverter().getWhite();
        while(howMuchMarbles!=0) {
            if (howMuchMarbles == 1) {
                int typeofres;
                do {
                    System.out.println("Hai una sola biglia bianca da convertire.\nIn che cosa vuoi convertirla?");
                    int i = 1;
                    for (ResourceType resourceType : game.getMe().getConverter().getToconvert()) {
                        System.out.println(i + ". " + resourceType);
                        i++;
                    }
                    typeofres = CLI.ReadFromKeyboard(stdin);
                } while (typeofres <= 0 || typeofres > game.getMe().getConverter().getToconvert().size());
                howMuchMarbles--;
                resourceTypes.add(game.getMe().getConverter().getToconvert().get(typeofres - 1));
            } else {
                System.out.println("Hai ancora " + howMuchMarbles + " biglie bianche da convertire.\nCome vuoi convertirle?");
                int i = 1;
                for (ResourceType resourceType : game.getMe().getConverter().getToconvert()) {
                    System.out.println(i + ". " + resourceType);
                    i++;
                }
                int typeofres = CLI.ReadFromKeyboard(stdin);
                if (typeofres <= 0 || typeofres > i) {
                    System.out.println("Invalid index!");
                } else {
                    System.out.println("Quante biglie vuoi convertire in " + game.getMe().getConverter().getToconvert().get(typeofres - 1) + "?");
                    int num = CLI.ReadFromKeyboard(stdin);
                    if (num < 0 || howMuchMarbles - num < 0) {
                        System.out.println("Invalid index!");
                    } else {
                        howMuchMarbles -= num;
                        for (int j = 0; j < num; j++) {
                            resourceTypes.add(game.getMe().getConverter().getToconvert().get(typeofres - 1));
                        }
                    }
                }
            }
            if(howMuchMarbles==0){
                if(game.getMe().getConverter().getWhite()==1){
                    System.out.println("Hai scelto di convertire la biglia bianca in "+resourceTypes.get(0)+"\n\tConfermi?\n1. YES\t2. NO");
                    int confirm=CLI.ReadFromKeyboard(stdin);
                    if(confirm==1){
                        move.setWhiteMarbles(resourceTypes);
                    }else{
                        howMuchMarbles=game.getMe().getConverter().getWhite();
                        resourceTypes = new ArrayList<ResourceType>();
                    }
                }else {
                    System.out.println("Hai scelto di convertire le " + game.getMe().getConverter().getWhite() + "biglie bianche in questo modo: ");
                    for(ResourceType r: ResourceType.values()){
                        int count=0;
                        for(ResourceType res: resourceTypes){
                            if(res.equals(r)) count++;
                        }
                        System.out.println(count+" "+r);
                    }
                    System.out.println("Confermi?\n1. YES\t2. NO");
                    int confirm=CLI.ReadFromKeyboard(stdin);
                    if(confirm==1){
                        move.setWhiteMarbles(resourceTypes);
                    }else{
                        howMuchMarbles=game.getMe().getConverter().getWhite();
                        resourceTypes = new ArrayList<ResourceType>();
                    }
                }
            }
        }
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
