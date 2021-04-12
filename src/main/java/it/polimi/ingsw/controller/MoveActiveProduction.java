package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.NumberOfResources;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProductionPower;
import it.polimi.ingsw.model.enumeration.PlayerStatus;

import javax.swing.text.View;
import java.util.ArrayList;

public class MoveActiveProduction extends MoveType{

    ProductionPower[] toActive;

    public MoveActiveProduction(Player player, View view, boolean isLastMove, ProductionPower[] toActive) {
        super(player);
        this.toActive = toActive;
    }

    @Override
    public boolean performMove(Game game){
        if(!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player= game.getCurrPlayer();
        if(player.getStatus() != PlayerStatus.Active){
            //TODO error Message
            return false;
        }

        //check if current player really own the productionPowers that want to active
        ArrayList<ProductionPower> productionOwned = player.getPersonalBoard().getProduction();

        for(ProductionPower p : toActive)
            if(!productionOwned.contains(p)) {
                //TODO error Message
                return false;
            }
        //sum all productionPower
        ProductionPower total = new ProductionPower();
        for(ProductionPower p : toActive)
            total = total.add(p);

        player.setToActive(total);

        if(player.isActivable()) {
            player.setToActive(null);
            //TODO error message
            return false;
        }

        player.setStatus(PlayerStatus.NeedToCHoseRes);

        if(player.easyActive()){
            //automatically perform the move if no choice is needed
            return new MoveChoseResources(player, new NumberOfResources(), new NumberOfResources()).performMove(game);

        }
        return true;
    }
}
