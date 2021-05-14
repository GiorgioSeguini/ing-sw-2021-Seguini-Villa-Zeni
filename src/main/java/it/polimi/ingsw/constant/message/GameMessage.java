package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.enumeration.GameStatus;

public class GameMessage implements Message{

    public static final String className = "GameMessage";
    private final GameStatus status;
    private final int index;

    public GameMessage(GameStatus status, int index) {
        this.status = status;
        this.index = index;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.setStatus(status);
        simpleGame.setIndex(index);
    }

    @Override
    public String getName() {
        return className;
    }
}
