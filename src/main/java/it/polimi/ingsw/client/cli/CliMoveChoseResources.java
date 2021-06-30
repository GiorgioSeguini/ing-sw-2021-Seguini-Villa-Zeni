package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

/**
 * CliMoveChoseResources class.
 * Implements CliInterface.
 * Manage the chose resources move on the cli.
 */
public class CliMoveChoseResources implements CliInterface{

    private final MoveChoseResources move;

    /**
     * Instantiates a new Cli move chose resources.
     *
     * @param myId of type int: the player's id.
     */
    public CliMoveChoseResources(int myId){
        this.move = new MoveChoseResources(myId);
    }

    /**
     * Update cli.
     * @param game of type GameClient: the game.
     * @param stdin of type Scanner:  the input scanner.
     * @return of type MoveType: the move.
     */
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
    }

    /**
     * @see MoveType#canPerform(Game)
     */
    @Override
    public boolean canPerform(GameClient game) {
        return move.canPerform(game);
    }

    /**
     *
     * @see MoveType#getClassName()
     */
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
