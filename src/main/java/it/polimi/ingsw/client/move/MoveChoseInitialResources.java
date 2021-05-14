package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.util.Scanner;

public class MoveChoseInitialResources extends MoveType {

    private NumberOfResources resources;
    public static final String className= "MoveChoseInitialResources";

    public MoveChoseInitialResources(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setResources(NumberOfResources resources) {
        this.resources = resources;
    }

    @Override
    public boolean canPerform(GameClient game) {
        if(!initialMove(game))
            return false;
        return game.getInitialResources()>game.getPlayerFromID(game.getMyID()).getDepots().getResources().size();
    }

    @Override
    public void updateCLI(Game game, Scanner in) {
        System.out.println("Scegli le risorse iniziali:");
        NumberOfResources resources= new NumberOfResources();

        for(ResourceType type : ResourceType.values()){
            System.out.println(type);
        }
        while(resources.size()< game.getInitialResources()){
            System.out.println("devi scegliere " + (game.getInitialResources() - resources.size()) + " risorse");
            int index = in.nextInt() -1;
            if(index>=0 && index < ResourceType.values().length){
                resources = resources.add(ResourceType.values()[index], 1);
            }else{
                System.out.println("invalid index");
            }
        }
        this.setResources(resources);
        System.out.println("ottima scelta");
    }
}
