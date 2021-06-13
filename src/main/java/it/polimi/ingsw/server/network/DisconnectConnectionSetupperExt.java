package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.DisconnectConnectionSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

public class DisconnectConnectionSetupperExt extends DisconnectConnectionSetupper implements Settable {
    public DisconnectConnectionSetupperExt(String playerName, String roomName) {
        super(playerName, roomName);
    }

    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        //server.getRoomFromName(setupper.getRoomName()).disconnectConnection(setupper.getPlayerName());
        socket.setActive(false);
        // TODO: 6/11/21  
    }

    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        if(server.findActiveRoom(setupper.getRoomName())){
            return server.getRoomFromName(setupper.getRoomName()).findPlayer(setupper.getPlayerName());
        }
        return false;
    }
}
