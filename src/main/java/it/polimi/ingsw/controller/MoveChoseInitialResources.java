package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
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
            player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }

        if(game.getPlayerIndex(player)<0){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }

        if(game.getInitialResources(player)!=resources.size()){
            player.setErrorMessage(ErrorMessage.BadChoice);
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
