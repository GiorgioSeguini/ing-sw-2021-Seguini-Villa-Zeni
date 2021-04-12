package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.exception.NoSpaceException;
import it.polimi.ingsw.model.exception.OutOfResourcesException;

import javax.swing.text.View;
import java.util.ArrayList;

public class MoveBuyDevCard extends MoveType{

    private final int pos;
    DevelopmentCard cardToBuy;

    public MoveBuyDevCard(Player active, int pos, DevelopmentCard cardToBuy) {
        super(active);
        this.pos = pos;
        this.cardToBuy = cardToBuy;
    }

    public int getPos() {
        return pos;
    }

    public DevelopmentCard getCardToBuy() {
        return cardToBuy;
    }

    @Override
    public boolean performMove(Game game) {
        if (!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player = game.getCurrPlayer();
        if (player.getStatus() != PlayerStatus.Active) {
            //TODO error Message
            return false;
        }

        ArrayList<DevelopmentCard>[] cardsOwned = player.getPersonalBoard().getOwnedDevCards();

        if(!game.getDashboard().getTopDevCard(cardToBuy.getColor(),cardToBuy.getLevel()).equals(cardToBuy)){
            //la carta che il player vuole comprare non è la prima della pila quindi deve sceglierne un'altra
            //TODO ERROR MESSAGE
            return false;
        }

        NumberOfResources realCost = cardToBuy.getCost().safe_sub(player.getDiscount());
        if (player.getDepots().match(realCost)) {
            try {
                player.getPersonalBoard().addDevCard(cardToBuy,pos);
            } catch (NoSpaceException e) {
                //TODO ERROR MESSAGE
                return false;
            }
            game.getDashboard().buyDevCard(cardToBuy.getColor(),cardToBuy.getLevel());
            try {
                player.getDepots().subResource(realCost);
            } catch (OutOfResourcesException ignored) {}
        } else{
            //qui bisogna dire al player che non può comprare quella carta perchè non ha abbastazna risorse e quindi di sceglierne un'altra
            //TODO ERROR MESSAGE
        }

        player.setStatus(PlayerStatus.MovePerformed);
        return true;
    }
}
