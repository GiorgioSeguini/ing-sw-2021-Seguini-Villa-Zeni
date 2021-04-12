package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.enumeration.ResourceType;

import javax.swing.text.View;
import java.util.ArrayList;

public class MoveWhiteConversion extends MoveType{
    private final ArrayList<ResourceType> whiteMarbles;

    public MoveWhiteConversion(Player player, ArrayList<ResourceType> whitemarbles){
        super(player);
        this.whiteMarbles=whitemarbles;
    }

    public ArrayList<ResourceType> getWhiteMarbles() {
        return whiteMarbles;
    }

    @Override
    public boolean performMove(Game game) {
        if (!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player = game.getCurrPlayer();
        if (player.getStatus() != PlayerStatus.NeedToConvert) {
            //TODO error Message
            return false;
        }

        if(player.getConverter().CheckIntegrityToConvert(whiteMarbles)){
            player.getConverter().WhiteMarbleConverter(whiteMarbles);
        }
        else{
            return false;
        }
        player.setStatus(PlayerStatus.MovePerformed);
        return true;
    }

}
