package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.modelClient.Game;
import it.polimi.ingsw.constant.enumeration.GameStatus;

public class GameMessage implements Message{

    private final GameStatus status;
    private final int index;

    public GameMessage(GameStatus status, int index) {
        this.status = status;
        this.index = index;
    }

    @Override
    public void handleMessage(Game simpleGame) {
        simpleGame.setStatus(status);
        simpleGame.setIndex(index);
    }
}
