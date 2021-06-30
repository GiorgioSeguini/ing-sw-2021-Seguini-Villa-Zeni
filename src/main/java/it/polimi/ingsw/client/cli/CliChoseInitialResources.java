package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseInitialResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

/**
 * CliChoseInitialResources class.
 * Implements CliInterface.
 * Manage the chose initial resources move on the cli.
 */
public class CliChoseInitialResources implements CliInterface {

    final MoveChoseInitialResources move;

    /**
     * Instantiates a new Cli chose initial resources.
     *
     * @param myId of type int: the player's id.
     */
    public CliChoseInitialResources(int myId) {
        this.move = new MoveChoseInitialResources(myId);
    }

    /**
     *
     * @param game of type GameClient: the game.
     * @param in of type Scanner: the input scanner.
     * @return of type MoveType: the move set.
     */
    @Override
    public MoveType updateCLI(GameClient game, Scanner in) {
        System.out.println("Scegli le risorse iniziali:");
        NumberOfResources resources= new NumberOfResources();

        int i=1;
        for(ResourceType type : ResourceType.values()){
            System.out.println(" "+i+". "+type);
            i++;
        }
        while(resources.size()< game.getInitialResources(move.getIdPlayer())){
            System.out.println("--> devi scegliere " + (game.getInitialResources(move.getIdPlayer()) - resources.size()) + " risorse");
            System.out.println("Ricorda! Digita il numero e poi premi INVIO");
            int index = CLI.ReadFromKeyboard(in) -1;
            if(index>=0 && index < ResourceType.values().length){
                resources = resources.add(ResourceType.values()[index], 1);
            }else{
                System.out.println("invalid index");
            }
        }
        move.setResources(resources);
        System.out.println("Ottima scelta!");
        return move;
    }

    /**
     * Check if the player can perform the move.
     * @param game of type GameClient: the game.
     * @return of type boolean: True if the player can perform the move. Otherwise False.
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
