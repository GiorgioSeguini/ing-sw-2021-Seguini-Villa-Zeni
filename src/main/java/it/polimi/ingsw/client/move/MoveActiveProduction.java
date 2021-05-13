package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.client.modelClient.ProductionPower;
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

    public void setToActive(ArrayList<ProductionPower> toActive) {
        this.toActive = toActive;
    }

    @Override
    public boolean canPerform(Game game) {
        return super.simpleCheck(game, allowedStatus);
    }
}
