package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;

public class PlayerMessage implements Message{

    public static final String className = "PlayerMessage";
    private final PlayerStatus status;
    private final int idPlayer;

    public PlayerMessage(PlayerStatus status, int idPlayer) {
        this.status = status;
        this.idPlayer = idPlayer;
    }

    @Override
    public void handleMessage(Client client) {
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(idPlayer);

        owner.setStatus(status);
    }

    @Override
    public String getName() {
        return className;
    }
}
