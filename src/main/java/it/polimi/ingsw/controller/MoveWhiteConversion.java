package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.ErrorMessage;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.enumeration.ResourceType;

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
