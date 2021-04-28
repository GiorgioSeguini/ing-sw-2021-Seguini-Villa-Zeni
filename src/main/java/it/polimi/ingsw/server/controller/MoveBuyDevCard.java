package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.enumeration.ErrorMessage;
import it.polimi.ingsw.server.model.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;


public class MoveBuyDevCard extends MoveType{

    private final int pos;
    DevelopmentCard cardToBuy;

    public MoveBuyDevCard(Player active, int pos, DevelopmentCard cardToBuy) {
        super(active);
        this.pos = pos;
        this.cardToBuy = cardToBuy;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.Active};
    }

    public int getPos() {
        return pos;
    }

    public DevelopmentCard getCardToBuy() {
        return cardToBuy;
    }

    @Override
    public boolean canPerform(Game game){

        if(!super.canPerform(game)) return false;

        if(!cardToBuy.equals(game.getDashboard().getTopDevCard(cardToBuy.getColor(),cardToBuy.getLevel()))){
            //la carta che il player vuole comprare non è la prima della pila quindi deve sceglierne un'altra
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }

        NumberOfResources realCost = cardToBuy.getCost().safe_sub(player.getDiscount());
        if (!player.getDepots().match(realCost)){
            //qui bisogna dire al player che non può comprare quella carta perchè non ha abbastazna risorse e quindi di sceglierne un'altra
            player.setErrorMessage(ErrorMessage.OutOfResourcesError);
        }

        return true;
    }

    @Override
    public void performMove(Game game) {
        NumberOfResources realCost = cardToBuy.getCost().safe_sub(player.getDiscount());
        try {
            player.getPersonalBoard().addDevCard(cardToBuy,pos);
        } catch (NoSpaceException e) {
            player.setErrorMessage(e.getErrorMessage());
            return;
        }
        game.getDashboard().buyDevCard(cardToBuy.getColor(),cardToBuy.getLevel());
        try {
            player.getDepots().subResource(realCost);
        } catch (OutOfResourcesException ignored) {}

        player.setStatus(PlayerStatus.MovePerformed);
    }
}