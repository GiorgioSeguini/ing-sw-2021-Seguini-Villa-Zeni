package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.util.ArrayList;

public class MoveWhiteConversion extends MoveType{
    private final ArrayList<ResourceType> whiteMarbles;
    public static final String className= "MoveWhiteConversion";

    public MoveWhiteConversion(int idPlayer, ArrayList<ResourceType> whitemarbles){
        super(idPlayer);
        this.whiteMarbles=whitemarbles;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToConvert};
    }

    public ArrayList<ResourceType> getWhiteMarbles() {
        return whiteMarbles;
    }

    @Override
    public boolean canPerform(Game game){
        if(!super.canPerform(game)) return false;

        Player player =game.getPlayerFromID(getIdPlayer());
        if(!player.getConverter().CheckIntegrityToConvert(whiteMarbles)){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }
        return true;
    }

    @Override
    public void performMove(Game game) {
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);
        player.getConverter().WhiteMarbleConverter(whiteMarbles);
        player.setStatus(PlayerStatus.MovePerformed);
    }

    @Override
    public String getClassName() {
        return className;
    }

}
