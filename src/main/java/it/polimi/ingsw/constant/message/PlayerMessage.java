package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.ProductionPower;

public class PlayerMessage implements Message{

    public static final String className = "PlayerMessage";
    private final PlayerStatus status;
    private final int idPlayer;
    private final ErrorMessage error;
    private final ProductionPower toActive;
    private final NumberOfResources discount;

    public PlayerMessage(PlayerStatus status, int idPlayer, ErrorMessage errorMessage, ProductionPower toActive, NumberOfResources discount) {
        this.status = status;
        this.idPlayer = idPlayer;
        this.error = errorMessage;
        this.toActive=toActive;
        this.discount = discount;
    }

    @Override
    public void handleMessage(Client client) {
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(idPlayer);
        owner.setErrorMessage(error);
        owner.setStatus(status);
        owner.setToActive(toActive);
        owner.setDiscounted(discount);
    }

    @Override
    public String getName() {
        return className;
    }
}
