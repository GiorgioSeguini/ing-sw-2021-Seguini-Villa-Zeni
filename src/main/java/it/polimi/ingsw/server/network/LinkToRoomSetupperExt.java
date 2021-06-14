package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.message.InitialMessage;
import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import it.polimi.ingsw.constant.setupper.SetUp;

import java.util.ArrayList;

public class LinkToRoomSetupperExt extends LinkToRoomSetupper implements Settable {

    public LinkToRoomSetupperExt(String playerName, String roomName) {
        super(playerName, roomName);
    }

    @Override
    public void setAction(Server server, SocketClientConnection socket, SetUp setupper) {
        Room room= server.getRoomFromName(setupper.getRoomName());

        if(server.findActiveRoom(room.getRoomName())){
            room.reconnectConnection(setupper.getPlayerName(),socket);
        }
        else {
            room.addConnection(setupper.getPlayerName(),socket);
            if(room.getConnections().size()==room.getNumOfPlayers()){
                server.startGame(room);
                room.setActive();
                server.removeRoom(room);
                server.addActiveRoom(room);
            }
        }

    }

    @Override
    public boolean canSetAction(Server server, SetUp setupper) {
        if(server.findActiveRoom(setupper.getRoomName())){
            return server.getRoomFromName(setupper.getRoomName()).findDisconnectedPlayer(setupper.getPlayerName());
        }
        else{
            if(server.findRoom(setupper.getRoomName())){
                return !server.getRoomFromName(setupper.getRoomName()).findPlayer(setupper.getPlayerName());
            }
        }
        return false;
    }
}
