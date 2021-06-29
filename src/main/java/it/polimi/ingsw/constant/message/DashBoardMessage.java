package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Dashboard;
import it.polimi.ingsw.constant.model.Game;

/**
 * DashBoardMessage class.
 * Implements Message interface.
 * Manage the dashboard's messages.
 */
public class DashBoardMessage implements Message{

    public static final String className = "DashBoardMessage";
    private final Dashboard dashBoard;

    /**
     * Instantiates a new Dash board message.
     *
     * @param dashBoard of type Dashboard: the dashboard.
     */
    public DashBoardMessage(Dashboard dashBoard){
        this.dashBoard= dashBoard;
    }

    /**
     * Handle the dashboard's message.
     * @param client of type Client: reference to the client.
     */
    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.setDashboard(dashBoard);
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
