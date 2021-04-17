package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductionPower;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
import it.polimi.ingsw.model.enumeration.PlayerStatus;


import java.util.ArrayList;

public class MoveActiveProduction extends MoveType{

    ProductionPower[] toActive;

    public MoveActiveProduction(Player player, ProductionPower[] toActive) {
        super(player);
        this.toActive = toActive;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.Active};
    }

    @Override
    public boolean canPerform(Game game){

        if(!super.canPerform(game)) return false;


        //check if current player really own the productionPowers that want to active
        ArrayList<ProductionPower> productionOwned = player.getPersonalBoard().getProduction();

        for(ProductionPower p : toActive) {
            if (!productionOwned.contains(p)) {
                //TODO
                return false;
            }
        }

        return true;
    }

    @Override
    public void performMove(Game game){
        //sum all productionPower
        ProductionPower total = new ProductionPower();
        for(ProductionPower p : toActive)
            total = total.add(p);

        player.setToActive(total);

        if(player.isActivable()) {
            player.setToActive(null);
            //TODO error message
            return;
        }

        player.setStatus(PlayerStatus.NeedToChoseRes);

        if(player.getToActive().easyActive()){
            //automatically perform the move if no choice is needed
            new MoveChoseResources(player, new NumberOfResources(), new NumberOfResources()).performMove(game);

        }
    }
}
