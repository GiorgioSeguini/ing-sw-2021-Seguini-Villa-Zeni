package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.move.MoveWhiteConversion;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.PlayerExt;

public class MoveWhiteConversionExt extends MoveWhiteConversion implements Performable {

    public MoveWhiteConversionExt(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public boolean canPerform(GameExt game){
        if(!super.canPerform(game)) return false;

        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        if(!player.getConverter().CheckIntegrityToConvert(getWhiteMarbles())){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }
        return true;
    }

    @Override
    public void performMove(GameExt game) {
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);
        player.getConverter().WhiteMarbleConverter(getWhiteMarbles());
        player.setStatus(PlayerStatus.MovePerformed);
    }

    @Override
    public String getClassName() {
        return className;
    }

}
