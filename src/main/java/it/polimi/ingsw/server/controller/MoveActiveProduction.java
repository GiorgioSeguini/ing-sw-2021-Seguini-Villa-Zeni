package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.ProductionPower;


import java.util.ArrayList;

public class MoveActiveProduction extends MoveType{

    ArrayList<ProductionPower> toActive;
    public static final String className= "MoveActiveProduction";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    public MoveActiveProduction(int idPlayer, ArrayList<ProductionPower> toActive) {
        super(idPlayer);
        this.toActive = toActive;
    }

    @Override
    public boolean canPerform(Game game){
        if(!super.simpleCheck(game, allowedStatus)) return false;
        Player player =game.getPlayerFromID(getIdPlayer());

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
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

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
            new MoveChoseResources(this.getIdPlayer(), new NumberOfResources(), new NumberOfResources()).performMove(game);

        }
    }

    @Override
    public String getClassName() {
        return className;
    }
}
