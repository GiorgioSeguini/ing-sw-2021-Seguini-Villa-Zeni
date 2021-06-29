package it.polimi.ingsw.server.network;
import it.polimi.ingsw.constant.setupper.SetUp;

/**
 * Interface meant to be used for the Setupper.
 * @see it.polimi.ingsw.constant.setupper.SetUp
 */
public interface Settable {

    /**
     * Method that makes the Setupper action.
     * @param server
     * @param socket
     * @param setupper
     */
    void setAction(Server server, SocketClientConnection socket, SetUp setupper);

    /**
     * Method that checks if a Setupper action can be done.
     * @param server
     * @param setupper
     * @return true if the Setupper action can be done.
     */
    boolean canSetAction(Server server, SetUp setupper);

}
