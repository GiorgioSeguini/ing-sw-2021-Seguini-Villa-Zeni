package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

public class JoinWaitingListSetupperExt extends JoinWaitngListSetupper implements Settable {

    public JoinWaitingListSetupperExt(String playerName, int numOfPlayer) {
        super(playerName, numOfPlayer);
    }

    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        server.lobby(socket, setupper.getPlayerName(), setupper.getNumOfPlayers());
    }

    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        return server.findName(setupper.getPlayerName(),getNumOfPlayers());
    }
}
