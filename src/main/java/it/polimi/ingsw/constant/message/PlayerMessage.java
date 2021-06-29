package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.Player;
import it.polimi.ingsw.constant.model.ProductionPower;

/**
 * PlayerMessage class.
 * Implements Message interface.
 * Manage the player messages.
 */
public class PlayerMessage implements Message{

    public static final String className = "PlayerMessage";
    private final PlayerStatus status;
    private final int idPlayer;
    private final ErrorMessage error;
    private final ProductionPower toActive;
    private final NumberOfResources discount;

    /**
     * Instantiates a new Player message.
     *
     * @param status of type PlayerStatus: the player's status.
     * @param idPlayer of type int: the player's ID.
     * @param errorMessage of type ErrorMessage: the error message.
     * @param toActive of type ProductionPower: the production power to active.
     * @param discount of type NumberOfResources: the discount on the resources.
     */
    public PlayerMessage(PlayerStatus status, int idPlayer, ErrorMessage errorMessage, ProductionPower toActive, NumberOfResources discount) {
        this.status = status;
        this.idPlayer = idPlayer;
        this.error = errorMessage;
        this.toActive=toActive;
        this.discount = discount;
    }

    /**
     * Handle the player messages.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client) {
        Game simpleGame = client.getSimpleGame();
        Player owner = simpleGame.getPlayerFromID(idPlayer);
        owner.setErrorMessage(error);
        owner.setStatus(status);
        owner.setToActive(toActive);
        owner.setDiscounted(discount);
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
