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
            if(room.findDisconnectedPlayer(setupper.getPlayerName())){
                room.reconnectConnection(setupper.getPlayerName(),socket);
                //room.getGame().notify(new InitialMessage(room.getGame(),room.getGame().getPlayerIdfromNickname(getPlayerName()), new ArrayList<>(), room.getRoomName()));
            }
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
        return server.findRoom(setupper.getRoomName());
    }
}
