package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;

import java.util.ArrayList;

public class MoveActiveProductionExt extends MoveActiveProduction implements Performable {

    public MoveActiveProductionExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerform(GameExt game){
        if(!super.canPerform(game)) return false;
        Player player =game.getPlayerFromID(getIdPlayer());

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

    @Override
    public void performMove(GameExt game){
        PlayerExt player = game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        //sum all productionPower
        ProductionPower total = new ProductionPower();
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
            //new MoveChoseResourcesExt(this.getIdPlayer(), new NumberOfResources(), new NumberOfResources()).performMove(game);
            //TODO
        }
    }

    @Override
    public String getClassName() {
        return className;
    }
}
