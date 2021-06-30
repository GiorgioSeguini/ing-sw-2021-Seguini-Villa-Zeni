package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * CliMoveActiveProduction class.
 * Implements CliInterface.
 * Manage the active production move on the cli.
 */
public class CliMoveActiveProduction implements CliInterface{
    private final MoveActiveProduction move;

    /**
     * Instantiates a new Cli move active production.
     *
     * @param myId of type int: the player's id
     */
    public CliMoveActiveProduction(int myId){
        this.move = new MoveActiveProduction(myId);
    }

    /**
     * Update cli.
     * @param game of type GameClient: the game.
     * @param stdin of type Scanner:  the input scanner.
     * @return of type MoveType: the move.
     */
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        ArrayList<ProductionPower> productionPowers = new ArrayList<ProductionPower>();
        int i=0;
        for(ProductionPower pp: game.getMe().getPersonalBoard().getProduction()){
            i++;
            System.out.println(i+".\t"+pp);
        }
        System.out.println("Risorse attuali totali: "+ game.getMe().getDepots().getResources());
        System.out.println("Quali produzioni vuoi attivare?\n------> Inserisci le produzioni e premi -1 per terminare. Se hai cambiato idea e vuoi tornare alle scelte iniziali premi direttamente -1.");
        int p;
        do{
            p = CLI.ReadFromKeyboard(stdin);
            if(p>0 && p<=i ) {
                productionPowers.add(game.getMe().getPersonalBoard().getProduction().get(p - 1));
            }else if(p!=-1){
                System.out.println("Invalid index!\n\tRIPROVA!");
            }
        }while(p!=-1);
        if(productionPowers.size()==0) return null;
        move.setToActive(productionPowers);
        return move;
    }

    /**
     * @see MoveType#canPerform(Game)
     */
    @Override
    public boolean canPerform(GameClient game) {
        if(game.getMe().getDepots().getResources().size()>=2){
            return move.canPerform(game);
        }
        return false;
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
