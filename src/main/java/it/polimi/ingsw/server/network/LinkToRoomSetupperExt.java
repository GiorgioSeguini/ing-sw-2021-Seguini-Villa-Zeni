package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

public class LinkToRoomSetupperExt extends LinkToRoomSetupper implements Settable {

    public LinkToRoomSetupperExt(String playerName, String roomName) {
        super(playerName, roomName);
    }

    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        Room room= server.getRoomFromName(setupper.getRoomName());
        if(server.findActiveRoom(room.getRoomName())){
            //todo player in un game già avviato riconnessione
        }
        else {
            room.addConnection(setupper.getPlayerName(),socket);
            if(room.getConnections().size()==room.getNumOfPlayers()){
                server.startGame(room);
                server.addActiveRoom(room);
                server.removeRoom(room);
            }
        }

    }

    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        if(server.checkPlayerName(setupper.getPlayerName())){
            return server.findRoom(setupper.getRoomName());
        }
        return false;
    }
}
