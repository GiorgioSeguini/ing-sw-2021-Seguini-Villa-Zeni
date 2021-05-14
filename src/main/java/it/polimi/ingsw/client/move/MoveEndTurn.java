package it.polimi.ingsw.client.move;

import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

import java.util.Scanner;

public class MoveEndTurn extends MoveType {

    public static final String className= "MoveEndTurn";
    private static final PlayerStatus[] allowedStatus = new PlayerStatus[]{PlayerStatus.MovePerformed};

    public MoveEndTurn(int idPlayer) {
        super(idPlayer);
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public boolean canPerform(Game game) {
        return simpleCheck(game, allowedStatus);
    }

    @Override
    public void updateCLI(Game game, Scanner stdin) {
        System.out.println("Fine turno");
    }
}
