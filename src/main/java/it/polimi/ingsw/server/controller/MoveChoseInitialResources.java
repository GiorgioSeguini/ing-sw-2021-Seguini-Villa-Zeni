package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.server.model.exception.UnableToFillException;

public class MoveChoseInitialResources extends MoveType{

    NumberOfResources resources;
    public static final String className= "MoveChoseInitialResources";

    public MoveChoseInitialResources(int idPlayer, NumberOfResources resources) {
        super(idPlayer, className);
        this.resources = resources;
    }

    @Override
    public boolean canPerform(Game game){
        Player player =game.getPlayerFromID(getIdPlayer());
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
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        try{
            player.getDepots().addResourcesFromMarket(resources);
        }catch(UnableToFillException ignored){}

        game.updateStatus();
    }

}
