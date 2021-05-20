package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.ArrayList;
import java.util.Scanner;

public class CliMoveActiveProduction implements CliInterface{
    private MoveActiveProduction move;

    public CliMoveActiveProduction(int myId){
        this.move = new MoveActiveProduction(myId);
    }
    @Override
    public MoveType updateCLI(GameClient game, Scanner stdin) {
        ArrayList<ProductionPower> productionPowers = new ArrayList<ProductionPower>();
        int i=1;
        for(ProductionPower pp: game.getMe().getPersonalBoard().getProduction()){
            System.out.println(i+".\t"+pp);
        }
        System.out.println("Quali produzioni vuoi attivare?\n\t(premi -1 per concludere).");
        int p=stdin.nextInt();
        while(p!=-1){
            if(p>=1&&p<=6) {
                productionPowers.add(game.getMe().getPersonalBoard().getProduction().get(p - 1));
            }else{
                System.out.println("Invalid index!\n\tRIPROVA!");
            }
            p = stdin.nextInt();
        }
        move.setToActive(productionPowers);
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
