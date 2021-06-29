package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

/**
 *LinkToRoomSetupper for the server package.
 * Menage to perform a LinkToRoomSetupper action.
 * @see it.polimi.ingsw.server.network.Settable
 * @see LinkToRoomSetupper
 */
public class LinkToRoomSetupperExt extends LinkToRoomSetupper implements Settable {

    /**
     * Default constructor.
     * Notice: setting number of playing players is an unnecessary information.
     *
     * @param playerName
     * @param roomName
     */
    public LinkToRoomSetupperExt(String playerName, String roomName) {
        super(playerName, roomName);
    }

    /**
     * It menages connection or reconnection to a room. If a room is full move the room from the default
     * server room list to the server active room list and starts the game.
     * @param server
     * @param socket
     * @param setupper
     */
    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        Room room= server.getRoomFromName(setupper.getRoomName());

        if(server.findActiveRoom(room.getRoomName())){
            room.reconnectConnection(setupper.getPlayerName(),socket);
        }
        else {
            room.addConnection(setupper.getPlayerName(),socket);
            if(room.isFull()){
                server.startGame(room);
                room.setActive();
                server.removeRoom(room);
                server.addActiveRoom(room);
            }
        }

    }

    /**
     * Checks if a LinkToRoomSetupper action can be done.
     * @param server
     * @param setupper
     * @return true if finds the disconnected player in an active room or if it doesn't find the player inside the existing room.
     */
    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        if(server.findActiveRoom(setupper.getRoomName())){
            return server.getRoomFromName(setupper.getRoomName()).findDisconnectedPlayer(setupper.getPlayerName());
        }
        else{
            if(server.findRoom(setupper.getRoomName())){
                try{
                    return !server.getRoomFromName(setupper.getRoomName()).findPlayer(setupper.getPlayerName());

                }catch (NullPointerException ignored){ /*if it enters here it is why he has tryed to enter a defaultRoom manually*/}
            }
        }
        return false;
    }
}
