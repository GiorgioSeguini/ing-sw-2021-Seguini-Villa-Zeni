package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.GameStatus;
import it.polimi.ingsw.model.exception.UnableToFillException;

public class MoveChoseInitialResources extends MoveType{

    NumberOfResources resources;

    public MoveChoseInitialResources(Player player, NumberOfResources resources) {
        super(player);
        this.resources = resources;
    }

    @Override
    public boolean canPerform(Game game){
        if(game.getStatus() != GameStatus.Initial){
            //TODO errror message
            return false;
        }

        if(game.getPlayerIndex(player)<0){
            //TODO error message
            return false;
        }

        if(game.getInitialResources(player)!=resources.size()){
            //TODO error message
            return false;
        }

        return true;
    }

    @Override
    public void performMove(Game game){

        try{
            player.getDepots().addResourcesFromMarket(resources);
        }catch(UnableToFillException ignored){}

        game.updateStatus();
    }

}
