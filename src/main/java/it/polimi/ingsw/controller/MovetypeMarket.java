package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Market;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.MarbleColor;
import it.polimi.ingsw.model.enumeration.PlayerStatus;
import it.polimi.ingsw.model.exception.HaveToChooseException;

import javax.swing.text.View;
import java.util.ArrayList;

public class MovetypeMarket extends MoveType{

    private final int indexToBuy;

    public MovetypeMarket(Player player, View view, boolean isLastMove, int indextobuy){
        super(player);
        this.indexToBuy=indextobuy;
    }

    public int getIndextobuy() {
        return indexToBuy;
    }

    private ArrayList<MarbleColor> takeMarbles(int indexToBuy, Market market){
        ArrayList<MarbleColor> buyedresources = null;

        /*Compro la colonna o la riga corretta*/
        if(indexToBuy>=0 && indexToBuy<=3)
            buyedresources = market.buyColumn(indexToBuy);

        if(indexToBuy>=4 && indexToBuy<=6)
            buyedresources = buyedresources = market.buyRow(indexToBuy - 4);

        if(buyedresources==null);
        //TODO check

        return buyedresources;
    }

    /**This method tries to convert the Marble as the player asked. If it can it makes the conversion
     * and stores the converted resources in the Converter Class. Returns TRUE if the conversion ends correctly,
     * returns FALSE if it doesn't.*/
    @Override
    public boolean performMove(Game game){
        if(!game.getCurrPlayer().equals(player))
            //TODO error Message
            return false;

        player= game.getCurrPlayer();
        if(player.getStatus() != PlayerStatus.Active){
            //TODO error Message
            return false;
        }

        try {
            player.getConverter().convertAll(takeMarbles(indexToBuy, game.getMarketTray()));
            player.setStatus(PlayerStatus.NeedToStore);
        }
        catch (HaveToChooseException error) {
            player.setStatus(PlayerStatus.NeedToConvert);
        }
        return true;
    }
}
