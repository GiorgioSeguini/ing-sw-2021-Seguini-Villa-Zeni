package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.enumeration.ErrorMessage;
import it.polimi.ingsw.server.model.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.enumeration.ResourceType;

import java.util.ArrayList;

public class MoveWhiteConversion extends MoveType{
    private final ArrayList<ResourceType> whiteMarbles;

    public MoveWhiteConversion(Player player, ArrayList<ResourceType> whitemarbles){
        super(player);
        this.whiteMarbles=whitemarbles;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToConvert};
    }

    public ArrayList<ResourceType> getWhiteMarbles() {
        return whiteMarbles;
    }

    @Override
    public boolean canPerform(Game game){
        if(!super.canPerform(game)) return false;

        if(!player.getConverter().CheckIntegrityToConvert(whiteMarbles)){
            player.setErrorMessage(ErrorMessage.BadChoice);
            return false;
        }
        return true;
    }

    @Override
    public void performMove(Game game) {
        player.getConverter().WhiteMarbleConverter(whiteMarbles);
        player.setStatus(PlayerStatus.MovePerformed);
    }

}
