package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.DisconnectConnectionSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

public class DisconnectConnectionSetupperExt extends DisconnectConnectionSetupper implements Settable {
    public DisconnectConnectionSetupperExt(String playerName, String roomName) {
        super(playerName, roomName);
    }

    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {

    }

    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        return false;
    }
}
