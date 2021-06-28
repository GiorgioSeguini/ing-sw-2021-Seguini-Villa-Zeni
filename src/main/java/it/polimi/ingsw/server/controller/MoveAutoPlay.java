package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.server.model.GameExt;

import java.util.ArrayList;

/**
 * MoveAutoPlay class.
 * Implements Performable interface.
 * Class used when a player is disconnected and keep the game going.
 */
public class MoveAutoPlay implements Performable{

    private final int myID;

    /**
     * Instantiates a new Move auto play.
     *
     * @param myID of type int: the disconnected player's id.
     */
    public MoveAutoPlay(int myID) {
        this.myID = myID;
    }

    /**
     * Check if the current player is the one who disconnected.
     * @param game of type GameExt: the game
     * @return True if the current player is the one who disconnected. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game) {
        return game.getCurrPlayer().getID()==myID;
    }

    /**
     * Method that perform the move.
     * Check in which status is the game and the current player, then perform a simple move and passes the turn.
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game) {
        if(game.getStatus()== GameStatus.Initial){
            if(new MoveChoseLeaderCards(myID).canPerform(game)){
                MoveChoseLeaderCardsExt move = new MoveChoseLeaderCardsExt(myID);
                ArrayList<LeaderCard> leaderCards = game.getActivableLeadCard(game.getPlayerFromID(myID));
                ArrayList <Integer> chosen = new ArrayList<>();
                chosen.add(leaderCards.get(0).getId());
                chosen.add(leaderCards.get(1).getId());
                move.setIndexLeaderCards(chosen);
                move.performMove(game);
            }
            MoveChoseInitialResourcesExt move1 = new MoveChoseInitialResourcesExt(myID);
            if(move1.canPerform(game)){
                int missing = game.getInitialResources(myID) - game.getPlayerFromID(myID).getDepots().getResources().size();
                move1.setResources(new NumberOfResources(missing, 0, 0, 0));
                move1.performMove(game);
            }
            return;
        }
        if(game.getStatus()==GameStatus.Running){
            if(game.getPlayerFromID(myID).getStatus() == PlayerStatus.Active || game.getPlayerFromID(myID).getStatus() == PlayerStatus.MovePerformed){
                game.nextTurn();
                return;
            }

            if (game.getPlayerFromID(myID).getStatus()== PlayerStatus.NeedToChoseRes) {
                int input = game.getPlayerFromID(myID).getToActive().getOfYourChoiceInput();
                NumberOfResources resIn = new NumberOfResources();
                int typeNum = 0;
                while (resIn.size() < input) {
                    if (game.getPlayerFromID(myID).getDepots().getResources().getAmountOf(ResourceType.values()[typeNum]) > 0 && resIn.getAmountOf(ResourceType.values()[typeNum])<game.getPlayerFromID(myID).getDepots().getResources().getAmountOf(ResourceType.values()[typeNum])) {
                        resIn = resIn.add(ResourceType.values()[typeNum], 1);
                    } else {
                        typeNum++;
                    }
                }
                int output = game.getPlayerFromID(myID).getToActive().getOfYourChoiceOutput();
                MoveChoseResourcesExt move2 = new MoveChoseResourcesExt(myID, resIn, new NumberOfResources(output, 0, 0, 0));
                move2.performMove(game);
                new MoveEndTurnExt(myID).performMove(game);
                return;
            }

            if (game.getPlayerFromID(myID).getStatus()== PlayerStatus.NeedToConvert) {
                MoveWhiteConversionExt move1 = new MoveWhiteConversionExt(myID);
                int missing = game.getPlayerFromID(myID).getConverter().getWhite();
                ArrayList<ResourceType> conversion = new ArrayList<>();
                for (int i = 0; i < missing; i++) {
                    conversion.add(game.getPlayerFromID(myID).getConverter().getToconvert().get(0));
                }
                move1.setWhiteMarbles(conversion);
                move1.performMove(game);
                //do not return, I'll need to store
            }
            if(game.getPlayerFromID(myID).getStatus()== PlayerStatus.NeedToStore){
                NumberOfResources resources = game.getPlayerFromID(myID).getConverter().getResources();
                MoveDiscardResourcesExt move0 = new MoveDiscardResourcesExt(myID);
                move0.setToDiscard(resources);
                move0.performMove(game);
                new MoveEndTurnExt(myID).performMove(game);
            }
        }
    }

    /**
     *
     * @return of type int: disconnected player's ID.
     */
    @Override
    public int getIdPlayer() {
        return myID;
    }
}
