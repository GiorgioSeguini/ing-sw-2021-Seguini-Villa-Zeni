package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.move.MoveLeader;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.LeaderCardExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;


public class MoveLeaderExt extends MoveLeader implements Performable {

    public MoveLeaderExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerformExt(GameExt game){
        if(!super.canPerform(game)) return false;

        LeaderCard leaderCard = game.findLeaderCard(getIdLeaderCard());
        if(leaderCard==null) return false;

        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        boolean isPresent = false;
        //try {
            for (LeaderCard c : player.getPersonalBoard().getLeaderCards())
                if (c.getId()==leaderCard.getId()) {
                    isPresent = true;
                }
        /*} catch (NoMoreLeaderCardAliveException e) {
            //Il player non ha più carte leader in mano
            player.setErrorMessage(e.getErrorMessage());
            return false;
        }*/

        if (!isPresent) {
            player.setErrorMessage(ErrorMessage.CardNotOwned);
            return false;
        }

        return true;
    }

    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        LeaderCardExt leaderCard = game.findLeaderCard(getIdLeaderCard());

        player.setErrorMessage(ErrorMessage.NoError);

        if (getMove() == 0) {
            if (!leaderCard.setPlayed(player)) {
                player.setErrorMessage(ErrorMessage.BadChoice);
                return;
            }
        }

        if (getMove() == 1)
            if (!leaderCard.setDiscard(player)) {
                player.setErrorMessage(ErrorMessage.BadChoice);
                //return;
            }
    }

    @Override
    public String getClassName() {
        return className;
    }
}
