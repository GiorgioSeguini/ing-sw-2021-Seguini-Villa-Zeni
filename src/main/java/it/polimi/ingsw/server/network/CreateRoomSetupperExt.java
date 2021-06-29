package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;


/**
 *CreateRoomSetupper for the server package.
 * Menage to perform a CreateRoomSetupper action.
 * @see it.polimi.ingsw.server.network.Settable
 * @see CreateRoomSetupper
 */

public class CreateRoomSetupperExt extends CreateRoomSetupper implements Settable {

    /**
     * Default constructor.
     *
     * @param playerName
     * @param roomName
     * @param numOfPlayers
     */
    public CreateRoomSetupperExt(String playerName, String roomName, int numOfPlayers) {
        super(playerName, roomName, numOfPlayers);
    }

    /**
     * Creates a new Room and adds it to the default Server Room list.
     * It also stores the connection of the player who sent the Setupper.
     * If the Room is meant to be for a single player It also starts the game
     * moving the room from the default server room list to the active server room list.
     * @param server
     * @param socket
     * @param setupper
     */
    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        Room room= new Room(setupper.getRoomName(), setupper.getNumOfPlayers());
        server.addRoom(room);
        room.addConnection(setupper.getPlayerName(), socket);
        if(room.getNumOfPlayers()==1){
            server.startGame(room);
            room.setActive();
            server.removeRoom(room);
            server.addActiveRoom(room);
        }
    }

    /**
     * A room can be created if its name is never used.
     * @param server
     * @param setupper
     * @return true if the room name is never used.
     */
    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        return !server.findRoom(setupper.getRoomName());
    }
}
