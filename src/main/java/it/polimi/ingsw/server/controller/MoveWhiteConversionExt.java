package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.move.MoveWhiteConversion;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;

/**
 * MoveWhiteConversionExt class.
 * Extends MoveWhiteConversion and implements Performable interface.
 * Manage the white conversion move.
 */
public class MoveWhiteConversionExt extends MoveWhiteConversion implements Performable {

    /**
     * Instantiates a new Move white conversion ext.
     *
     * @param idPlayer of type int: the player's ID.
     */
    public MoveWhiteConversionExt(int idPlayer) {
        super(idPlayer);
    }

    /**
     * Check if the player has the status to perform this move in this game (if the one hasn't, set the error message to MoveNotAllowed).
     * Set the error message on BadChoice if there are some problems in the converter.
     * @param game of type GameExt: the game
     * @return True if the player has the status to perform this move in this game and the converter is fine. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game){
        PlayerExt player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }

        if(!player.getConverter().CheckIntegrityToConvert(getWhiteMarbles())){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }
        return true;
    }

    /**
     * Method that perform the move and set the error message on NoError and the player status on NeedToStore.
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);
        player.getConverter().WhiteMarbleConverter(getWhiteMarbles());
        player.setStatus(PlayerStatus.NeedToStore);
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
