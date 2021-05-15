package it.polimi.ingsw.constant.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

import java.util.ArrayList;

public class MoveActiveProduction extends MoveType {

    private ArrayList<ProductionPower> toActive;
    public static final String className= "MoveActiveProduction";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.Active};

    public MoveActiveProduction(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public boolean canPerform(Game game) {
        return super.simpleCheck(game, allowedStatus);
    }

    public void setToActive(ArrayList<ProductionPower> toActive) {
        this.toActive = toActive;
    }

    public ArrayList<ProductionPower> getToActive() {
        return toActive;
    }
}
