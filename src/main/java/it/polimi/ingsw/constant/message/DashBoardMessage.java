package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Dashboard;
import it.polimi.ingsw.constant.model.Game;

public class DashBoardMessage implements Message{

    public static final String className = "DashBoardMessage";
    private final Dashboard dashBoard;

    public DashBoardMessage(Dashboard dashBoard){
        this.dashBoard= dashBoard;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.setDashboard(dashBoard);
    }

    @Override
    public String getName() {
        return className;
    }
}
