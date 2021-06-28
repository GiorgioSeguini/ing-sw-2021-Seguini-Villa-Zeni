package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.ArrayList;
import java.util.Scanner;

public class CliMoveActiveProduction implements CliInterface{
    private final MoveActiveProduction move;

    public CliMoveActiveProduction(int myId){
        this.move = new MoveActiveProduction(myId);
    }
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

    @Override
    public boolean canPerform(GameClient game) {
        if(game.getMe().getDepots().getResources().size()>=2){
            return move.canPerform(game);
        }
        return false;
    }

    @Override
    public String getName() {
        return move.getClassName();
    }
}
