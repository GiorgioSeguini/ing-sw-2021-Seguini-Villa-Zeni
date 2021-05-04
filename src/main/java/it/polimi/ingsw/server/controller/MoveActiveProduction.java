package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NumberOfResources;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.ProductionPower;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;


import java.util.ArrayList;

public class MoveActiveProduction extends MoveType{

    ArrayList<ProductionPower> toActive;

    public MoveActiveProduction(Player player, ArrayList<ProductionPower> toActive) {
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
                player.setErrorMessage(ErrorMessage.CardNotOwned);
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

        if(!player.isActivable()) {
            player.setToActive(null);
            player.setErrorMessage(ErrorMessage.OutOfResourcesError);
            return;
        }

        player.setStatus(PlayerStatus.NeedToChoseRes);

        if(player.getToActive().easyActive()){
            //automatically perform the move if no choice is needed
            new MoveChoseResources(player, new NumberOfResources(), new NumberOfResources()).performMove(game);

        }
    }
}
