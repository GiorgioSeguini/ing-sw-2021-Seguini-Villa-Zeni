package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

public class CreateRoomSetupperExt extends CreateRoomSetupper implements Settable {

    public CreateRoomSetupperExt(String playerName, String roomName, int numOfPlayers) {
        super(playerName, roomName, numOfPlayers);
    }

    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        Room room= new Room(setupper.getRoomName(), setupper.getNumOfPlayers());
        room.addConnection(setupper.getPlayerName(), socket);
        server.addRoom(room);
        server.addPlayerNickName(setupper.getPlayerName());
    }

    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        if(server.checkPlayerName(setupper.getPlayerName())){
            return !server.findRoom(setupper.getRoomName());
        }
        return false;
    }
}
