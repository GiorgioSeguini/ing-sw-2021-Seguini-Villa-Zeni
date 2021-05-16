package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;

import java.util.ArrayList;

public class MoveWhiteConversion extends MoveType {
    private ArrayList<ResourceType> whiteMarbles;
    public static final String className= "MoveWhiteConversion";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.NeedToConvert};

    public MoveWhiteConversion(int idPlayer){
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public ArrayList<ResourceType> getWhiteMarbles() {
        return whiteMarbles;
    }

    public void setWhiteMarbles(ArrayList<ResourceType> whiteMarbles) {
        this.whiteMarbles = new ArrayList<>();
        this.whiteMarbles.addAll(whiteMarbles);
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }
}