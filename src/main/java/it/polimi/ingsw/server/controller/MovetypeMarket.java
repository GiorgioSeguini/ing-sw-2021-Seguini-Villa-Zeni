package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Market;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.server.model.exception.HaveToChooseException;

import java.util.ArrayList;

public class MovetypeMarket extends MoveType{

    private final int indexToBuy;
    public static final String className= "MovetypeMarket";

    public MovetypeMarket(int idPlayer, int indextobuy){
        super(idPlayer, className);
        this.indexToBuy=indextobuy;
        this.allowedStatus = new PlayerStatus[]{PlayerStatus.Active};
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
            buyedresources = market.buyRow(indexToBuy - 4);

        return buyedresources;
    }

    @Override
    public boolean canPerform(Game game){
        return super.canPerform(game);
    }

    /**This method tries to convert the Marble as the player asked. If it can it makes the conversion
     * and stores the converted resources in the Converter Class. Returns TRUE if the conversion ends correctly,
     * returns FALSE if it doesn't.*/
    @Override
    public void performMove(Game game){
        Player player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        ArrayList<MarbleColor> marbles = takeMarbles(indexToBuy, game.getMarketTray());
        if(marbles==null) {
            player.setErrorMessage(ErrorMessage.BadChoice);
            return;
        }
        try {
            player.getConverter().convertAll(marbles);
            player.setStatus(PlayerStatus.NeedToStore);
        }
        catch (HaveToChooseException error) {
            player.setStatus(PlayerStatus.NeedToConvert);
        }
    }
}
