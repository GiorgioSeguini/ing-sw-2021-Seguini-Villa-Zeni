package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.exception.NoSpaceException;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;


/**
 * MoveBuyDevCardExt class.
 * Extends MoveBuyDevCard and implements Performable interface.
 * Manage the buy development card move.
 */
public class MoveBuyDevCardExt extends MoveBuyDevCard implements Performable {

    /**
     * Instantiates a new Move buy dev card ext.
     *
     * @param idPlayer of type int: the player's ID.
     */
    public MoveBuyDevCardExt(int idPlayer) {
        super(idPlayer);
    }

    /**
     * Instantiates a new Move buy dev card ext.
     *
     * @param idPlayer of type int: the player's ID.
     * @param indexCardToBuy of type int: the card to buy's index
     * @param pos of type int: the position where the player want to put the card.
     */
    public MoveBuyDevCardExt(int idPlayer, int indexCardToBuy, int pos) {
        super(idPlayer);
        setIndexCardToBuy(indexCardToBuy);
        setPos(pos);
    }

    /**
     * Check if the player has the status to perform this move in this game (if the one hasn't set the error message to MoveNotAllowed).
     * Also check if the card to buy isn't null and if it is the one on top of the dashboard's stack (error message = BadChoice).
     * Check if the player has enough resources to buy the card (in this case set the error message to OutOfResourcesError).
     * @param game of type GameExt: the game
     * @return True if the player can perform the move. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game){
        PlayerExt player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }

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
        if (!player.getDepots().match(realCost)){
            //qui bisogna dire al player che non può comprare quella carta perchè non ha abbastazna risorse e quindi di sceglierne un'altra
            player.setErrorMessage(ErrorMessage.OutOfResourcesError);
            return false;
        }

        return true;
    }

    /**
     * Method that perform the move and set the correct game and player's status.
     * Set the error message too (NoError if it is ok, NoSpaceError if there isn't space on the personal board)
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game) {
        PlayerExt player = game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        DevelopmentCard cardToBuy = game.getDashboard().findDevCard(getIndexCardToBuy());

        NumberOfResources realCost = cardToBuy.getCost().safe_sub(player.getDiscounted());
        try {
            player.getPersonalBoard().addDevCard(cardToBuy,getPos());
        } catch (NoSpaceException e) {
            player.setErrorMessage(e.getErrorMessage());
            return;
        } catch (IllegalArgumentException e) {
            player.setErrorMessage(ErrorMessage.BadChoice);
            return;
        }
        game.getDashboard().buyDevCard(cardToBuy.getColor(),cardToBuy.getLevel());
        try {
            player.getDepots().subResource(realCost);
        } catch (OutOfResourcesException ignored) {}

        player.setStatus(PlayerStatus.MovePerformed);
    }

}
