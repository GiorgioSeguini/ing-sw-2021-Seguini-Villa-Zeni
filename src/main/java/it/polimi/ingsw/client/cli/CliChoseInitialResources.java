package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseInitialResources;
import it.polimi.ingsw.constant.move.MoveType;

import java.util.Scanner;

public class CliChoseInitialResources implements CliInterface {

    MoveChoseInitialResources move;

    public CliChoseInitialResources(int myId) {
        this.move = new MoveChoseInitialResources(myId);
    }

    @Override
    public MoveType updateCLI(GameClient game, Scanner in) {
        System.out.println("Scegli le risorse iniziali:");
        NumberOfResources resources= new NumberOfResources();

        for(ResourceType type : ResourceType.values()){
            System.out.println(type);
        }
        while(resources.size()< game.getInitialResources(move.getIdPlayer())){
            System.out.println("devi scegliere " + (game.getInitialResources(move.getIdPlayer()) - resources.size()) + " risorse");
            int index = in.nextInt() -1;
            if(index>=0 && index < ResourceType.values().length){
                resources = resources.add(ResourceType.values()[index], 1);
            }else{
                System.out.println("invalid index");
            }
        }
        move.setResources(resources);
        System.out.println("ottima scelta");
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
