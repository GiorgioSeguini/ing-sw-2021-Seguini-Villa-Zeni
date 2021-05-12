package it.polimi.ingsw.client.move;

import it.polimi.ingsw.client.modelClient.ProductionPower;

import java.util.ArrayList;

public class MoveActiveProduction extends MoveType {

    ArrayList<ProductionPower> toActive;
    public static final String className= "MoveActiveProduction";

    public MoveActiveProduction(int idPlayer, ArrayList<ProductionPower> toActive) {
        super(idPlayer);
        this.toActive = toActive;
    }

    @Override
    public String getClassName() {
        return className;
    }

}
