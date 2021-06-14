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
        server.addRoom(room);
        room.addConnection(setupper.getPlayerName(), socket);
        if(room.getNumOfPlayers()==1){
            server.startGame(room);
            room.setActive();
            server.removeRoom(room);
            server.addActiveRoom(room);
        }
    }

    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        return !server.findRoom(setupper.getRoomName());
    }
}
