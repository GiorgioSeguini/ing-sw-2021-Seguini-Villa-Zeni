package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

/**
 *JoinWaitingListSetupper for the server package.
 * Menage to perform a JoinWaitingListSetupper action.
 * @see it.polimi.ingsw.server.network.Settable
 * @see JoinWaitngListSetupper
 */

public class JoinWaitingListSetupperExt extends JoinWaitngListSetupper implements Settable {

    /**
     * Default constuctor.
     * Notice: is a pblic room, so the room name is set by the server.
     *
     * @param playerName
     * @param numOfPlayer
     */
    public JoinWaitingListSetupperExt(String playerName, int numOfPlayer) {
        super(playerName, numOfPlayer);
    }

    /**
     * Calls the method "lobby" in Server.
     * @see Server
     * @param server
     * @param socket
     * @param setupper
     */
    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        server.lobby(socket, setupper.getPlayerName(), setupper.getNumOfPlayers());
    }


    /**
     * Checks if the player name is already taken in the default room.
     * @param server
     * @param setupper
     * @return true if the player name is not already taken in the default room.
     */
    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        return !server.findName(setupper.getPlayerName(),getNumOfPlayers());
    }
}
