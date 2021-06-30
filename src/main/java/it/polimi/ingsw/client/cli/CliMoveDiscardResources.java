package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveDiscardResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

/**
 * CliMoveDiscardResources class.
 * Implements CliInterface.
 * Manage the discard resources move on the cli.
 */
public class CliMoveDiscardResources implements CliInterface{

    private final MoveDiscardResources move;

    /**
     * Instantiates a new Cli move discard resources.
     *
     * @param myId of type int: the player's id.
     */
    public CliMoveDiscardResources(int myId){
        this.move = new MoveDiscardResources(myId);
    }

    /**
     * Update cli.
     * @param game of type GameClient: the game.
     * @param stdin of type Scanner:  the input scanner.
     * @return of type MoveType: the move.
     */
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
                num = CLI.ReadFromKeyboard(stdin);
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
}
