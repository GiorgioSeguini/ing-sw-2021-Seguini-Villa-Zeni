package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.DisconnectConnectionSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

/**
 *DisconnectConnectionSetupper for the server package.
 * Menage to perform a DisconnectConnectionSetupper action.
 * @see it.polimi.ingsw.server.network.Settable
 * @see DisconnectConnectionSetupper
 */

public class DisconnectConnectionSetupperExt extends DisconnectConnectionSetupper implements Settable {


    /**
     * Default constructor.
     *
     * @param playerName
     * @param roomName
     */
    public DisconnectConnectionSetupperExt(String playerName, String roomName) {
        super(playerName, roomName);
    }

    /**
     * It sets socket not active. The SocketClientConnection will call the method "close".
     * @see SocketClientConnection
     * @param server
     * @param socket
     * @param setupper
     */
    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        socket.setActive(false);
    }

    /**
     * Checks if a DisconnectConnectionSetupper action can be done.
     * @param server
     * @param setupper
     * @return true if it finds the player in the active room. False, if the room is not active or it cannot find the player.
     */
    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        if(server.findActiveRoom(setupper.getRoomName())){
            return server.getRoomFromName(setupper.getRoomName()).findPlayer(setupper.getPlayerName());
        }
        return false;
    }
}
