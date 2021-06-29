package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.LorenzoSoloPlayer;

/**
 * GameMessage class.
 * Implements Message interface.
 * Manage the game messages.
 */
public class GameMessage implements Message{

    public static final String className = "GameMessage";
    private final GameStatus status;
    private final int index;
    private final LorenzoSoloPlayer lorenzoSoloPlayer;

    /**
     * Instantiates a new Game message.
     *
     * @param status of type GameStatus: the status
     * @param index of type int: the index.
     * @param lorenzoSoloPlayer of type LorenzoSoloPlayer: the lorenzo solo player
     */
    public GameMessage(GameStatus status, int index, LorenzoSoloPlayer lorenzoSoloPlayer) {
        this.status = status;
        this.index = index;
        this.lorenzoSoloPlayer = lorenzoSoloPlayer;
    }

    /**
     * Handle game messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.setStatus(status);
        simpleGame.setIndex(index);
        simpleGame.setSoloGame(lorenzoSoloPlayer);
    }

    /**
     *
     * @return of type String: the class name, useful for json serialization.
     */
    @Override
    public String getName() {
        return className;
    }
}
