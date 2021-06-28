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
