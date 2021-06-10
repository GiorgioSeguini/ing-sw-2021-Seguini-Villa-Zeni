package it.polimi.ingsw.server.network;

import it.polimi.ingsw.constant.setupper.SetUp;

public interface Settable {

    void setAction(Server server, SocketClientConnection socket, SetUp setupper);

    boolean canSetAction(Server server, SetUp setupper);

}
