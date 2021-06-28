package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LorenzoSoloPlayer;

public class GameMessage implements Message{

    public static final String className = "GameMessage";
    private final GameStatus status;
    private final int index;
    private final LorenzoSoloPlayer lorenzoSoloPlayer;

    public GameMessage(GameStatus status, int index, LorenzoSoloPlayer lorenzoSoloPlayer) {
        this.status = status;
        this.index = index;
        this.lorenzoSoloPlayer = lorenzoSoloPlayer;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.setStatus(status);
        simpleGame.setIndex(index);
        simpleGame.setSoloGame(lorenzoSoloPlayer);
    }

    @Override
    public String getName() {
        return className;
    }
}
