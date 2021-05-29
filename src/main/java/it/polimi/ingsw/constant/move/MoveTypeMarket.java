package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

import java.util.Scanner;

public class MoveTypeMarket extends MoveType {

    private int indexToBuy; //from 0 to 6- from 0 to 3 for columns - from 4 to 6 for rows
    public static final String className = "MovetypeMarket";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    public MoveTypeMarket(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public int getIndexToBuy() {
        return indexToBuy;
    }

    public void setIndexToBuy(int indexToBuy) {
        this.indexToBuy = indexToBuy;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }


}


