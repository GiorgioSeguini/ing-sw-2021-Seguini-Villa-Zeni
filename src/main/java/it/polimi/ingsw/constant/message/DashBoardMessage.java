package it.polimi.ingsw.constant.message;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constant.model.Dashboard;
import it.polimi.ingsw.constant.model.Game;
import it.polimi.ingsw.client.parser.StarterClient;

public class DashBoardMessage implements Message{

    public static final String className = "DashBoardMessage";
    private final String dashBoard;

    public DashBoardMessage(String dashBoard){
        this.dashBoard= dashBoard;
    }

    @Override
    public void handleMessage(Client client){
        Game simpleGame = client.getSimpleGame();
        simpleGame.setDashboard(StarterClient.fromJson(dashBoard, Dashboard.class));
    }

    @Override
    public String getName() {
        return className;
    }
}
