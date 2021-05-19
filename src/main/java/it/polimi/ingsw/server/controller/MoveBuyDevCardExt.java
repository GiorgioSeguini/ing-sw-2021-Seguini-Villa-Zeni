package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;


public class MoveBuyDevCardExt extends MoveBuyDevCard implements Performable {

    public MoveBuyDevCardExt(int idPlayer) {
        super(idPlayer);
    }

    public MoveBuyDevCardExt(int idPlayer, int indexCardToBuy, int pos) {
        super(idPlayer);
        setIndexCardToBuy(indexCardToBuy);
        setPos(pos);
    }

    @Override
    public boolean canPerformExt(GameExt game){

        if(!super.canPerform(game)) return false;
        PlayerExt player = game.getPlayerFromID(getIdPlayer());

        DevelopmentCard cardToBuy = game.getDashboard().findDevCard(getIndexCardToBuy());

        if(cardToBuy== null){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }

        if(!cardToBuy.equals(game.getDashboard().getTopDevCard(cardToBuy.getColor(),cardToBuy.getLevel()))){
            //la carta che il player vuole comprare non è la prima della pila quindi deve sceglierne un'altra
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }

        NumberOfResources realCost = cardToBuy.getCost().safe_sub(player.getDiscounted());
        if (!((DepotsExt)player.getDepots()).match(realCost)){
            //qui bisogna dire al player che non può comprare quella carta perchè non ha abbastazna risorse e quindi di sceglierne un'altra
            player.setErrorMessage(ErrorMessage.OutOfResourcesError);
            return false;
        }

        return true;
    }

    @Override
    public void performMove(GameExt game) {
        PlayerExt player = game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        DevelopmentCard cardToBuy = game.getDashboard().findDevCard(getIndexCardToBuy());

        NumberOfResources realCost = cardToBuy.getCost().safe_sub(player.getDiscounted());
        try {
            ((PersonalBoardExt)player.getPersonalBoard()).addDevCard(cardToBuy,getPos());
        } catch (NoSpaceException e) {
            player.setErrorMessage(e.getErrorMessage());
            return;
        }
        game.getDashboard().buyDevCard(cardToBuy.getColor(),cardToBuy.getLevel());
        try {
            ((DepotsExt)player.getDepots()).subResource(realCost);
        } catch (OutOfResourcesException ignored) {}

        player.setStatus(PlayerStatus.MovePerformed);
    }

}
