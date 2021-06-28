package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.ProductionPowerExt;

import java.util.ArrayList;

/**
 * MoveActiveProductionExt class.
 * Extends MoveActiveProduction and implements Performable interface.
 * Manage the active production move.
 */
public class MoveActiveProductionExt extends MoveActiveProduction implements Performable {

    /**
     * Instantiates a new Move active production ext.
     *
     * @param idPlayer of type int: the player's ID who want to make the move.
     * @param productionPowers of type ArrayList<ProductionPowerExt>: the production powers that have to be activated.
     */
    public MoveActiveProductionExt(int idPlayer, ArrayList<ProductionPowerExt> productionPowers) {
        super(idPlayer);
        setToActive(new ArrayList<>(productionPowers));
    }

    /**
     * Check if the player has the status to perform this move in this game (if he hasn't set the error message to MoveNotAllowed).
     * Also check if the player hasn't the correct status (set error message to MoveNotAllowed) and if the player doesn't own the card (error message = CardNotOwned).
     * @param game of type GameExt: the game.
     * @return True if the player can make the move. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game){
        Player player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }


        //check if current player really own the productionPowers that want to active
        ArrayList<ProductionPower> productionOwned = player.getPersonalBoard().getProduction();

        for(ProductionPower p : getToActive()) {
            if (!productionOwned.contains(p)) {
                player.setErrorMessage(ErrorMessage.CardNotOwned);
                return false;
            }
        }

        return true;
    }

    /**
     * Method that perform the move and set the correct game and player's status.
     * Set the error message too (NoError if there isn't any error. OutOfResourcesError if the player doesn't own enough resources to perform the move).
     * @param game of type GameExt: the game.
     */
    @Override
    public void performMove(GameExt game){
        PlayerExt player = game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        //sum all productionPower
        ProductionPowerExt total = new ProductionPowerExt();
        for(ProductionPower p : getToActive())
            total = total.add(p);

        player.setToActive(total);

        if(!player.isActivable()) {
            player.setToActive(null);
            player.setErrorMessage(ErrorMessage.OutOfResourcesError);
            return;
        }

        player.setStatus(PlayerStatus.NeedToChoseRes);

        if(player.getToActive().easyActive()){
            //automatically perform the move if no choice is needed
            new MoveChoseResourcesExt(this.getIdPlayer(), new NumberOfResources(), new NumberOfResources()).performMove(game);
        }
    }

    /**
     *
     * @return of type String: the class name.
     */
    @Override
    public String getClassName() {
        return className;
    }
}
