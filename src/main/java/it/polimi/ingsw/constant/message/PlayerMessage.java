package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class PlayerMessage implements Message{

    public static final String className = "PlayerMessage";
    private final PlayerStatus status;
    private final int idPlayer;
    private final ErrorMessage error;

    public PlayerMessage(PlayerStatus status, int idPlayer, ErrorMessage errorMessage) {
        this.status = status;
        this.idPlayer = idPlayer;
        this.error = errorMessage;
    }

    @Override
    public void handleMessage(Client client) {
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(idPlayer);
        owner.setErrorMessage(error);
        owner.setStatus(status);
    }

    @Override
    public String getName() {
        return className;
    }
}
