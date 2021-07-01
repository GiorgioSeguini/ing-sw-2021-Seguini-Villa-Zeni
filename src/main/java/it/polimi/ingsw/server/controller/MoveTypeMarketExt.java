package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.move.MoveTypeMarket;
import it.polimi.ingsw.server.model.GameExt;
import it.polimi.ingsw.server.model.MarketExt;
import it.polimi.ingsw.server.model.PlayerExt;
import it.polimi.ingsw.server.model.exception.HaveToChooseException;

import java.util.ArrayList;

/**
 * MoveTypeMarketExt class.
 * Extends MoveTypeMarket and implements Performable interface.
 * Manage the market move.
 */
public class MoveTypeMarketExt extends MoveTypeMarket implements Performable{

    /**
     * Instantiates a new Move type market ext.
     *
     * @param idPlayer of type int: the player's ID.
     */
    public MoveTypeMarketExt(int idPlayer) {
        super(idPlayer);
    }

    private ArrayList<MarbleColor> takeMarbles(int indexToBuy, MarketExt market){
        ArrayList<MarbleColor> buyedresources = null;

        /*Compro la colonna o la riga corretta*/
        if(indexToBuy>=0 && indexToBuy<=3)
            buyedresources = market.buyColumn(indexToBuy);

        if(indexToBuy>=4 && indexToBuy<=6)
            buyedresources = market.buyRow(indexToBuy - 4);

        return buyedresources;
    }

    /**
     * Check if the player has the status to perform this move in this game (if the one hasn't, set the error message to MoveNotAllowed).
     * @param game of type GameExt: the game
     * @return True if the player has the status to perform this move in this game. Otherwise False.
     */
    @Override
    public boolean canPerformExt(GameExt game){
        Player player = game.getPlayerFromID(getIdPlayer());
        if(!super.canPerform(game)){
            if(player!=null)
                player.setErrorMessage(ErrorMessage.MoveNotAllowed);
            return false;
        }
        return true;
    }

    /**
     * Method that perform the move and buy the column or the row selected.
     * This method tries also to convert the Marble as the player asked.
     * If it can, it makes the conversion and stores the converted resources in the Converter Class and set the player status on NeedToStore and the game status on NoError.
     * Otherwise set the player status on NeedToConvert and the game status on BadChoice.
     * @param game of type GameExt: the game
     */
    @Override
    public void performMove(GameExt game){
        PlayerExt player =game.getPlayerFromID(getIdPlayer());
        player.setErrorMessage(ErrorMessage.NoError);

        ArrayList<MarbleColor> marbles = takeMarbles(getIndexToBuy(), game.getMarketTray());
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
        }finally {
            game.popesInspection();
        }
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
