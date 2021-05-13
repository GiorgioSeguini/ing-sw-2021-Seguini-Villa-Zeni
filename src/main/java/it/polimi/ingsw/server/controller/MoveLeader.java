package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.LeaderCard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.exception.NoMoreLeaderCardAliveException;


public class MoveLeader extends MoveType{

    int move;
    int idLeaderCard;
    public static final String className= "MoveLeader";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active, PlayerStatus.MovePerformed};

    public MoveLeader(int idPlayer, int move, int idLeaderCard) {
        super(idPlayer);
        this.move = move;
        this.idLeaderCard = idLeaderCard;
    }

    @Override
    public boolean canPerform(Game game){
        if(!super.simpleCheck(game, allowedStatus)) return false;

        LeaderCard leaderCard = game.findLeaderCard(idLeaderCard);
        if(leaderCard==null) return false;

        Player player =game.getPlayerFromID(getIdPlayer());
        boolean isPresent = false;
        try {
            for (LeaderCard c : player.getPersonalBoard().getLeaderCards())
                if (c.equals(leaderCard)) {
                    isPresent = true;
                    leaderCard = c;
                }
        } catch (NoMoreLeaderCardAliveException e) {
            //Il player non ha più carte leader in mano
            player.setErrorMessage(e.getErrorMessage());
            return false;
        }

        if (!isPresent) {
            player.setErrorMessage(ErrorMessage.CardNotOwned);
            return false;
        }

        return true;
    }

    @Override
    public void performMove(Game game) {
        Player player =game.getPlayerFromID(getIdPlayer());
        LeaderCard leaderCard = game.findLeaderCard(idLeaderCard);

        player.setErrorMessage(ErrorMessage.NoError);

        if (move == 0) {
            if (!leaderCard.setPlayed(player)) {
                player.setErrorMessage(ErrorMessage.BadChoice);
                return;
            }
        }

        if (move == 1)
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
