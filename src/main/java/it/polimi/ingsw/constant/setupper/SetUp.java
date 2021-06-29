package it.polimi.ingsw.constant.setupper;


/**
 * Abstract class SetUp. Setuppers are messages sent from the client to the server to instance a match or to menage a disconnection (also a reconnection).
 * The match can be set in two main ways: a public game or a private game with friends. The public game sets a public room always reachable, whereas in
 * the private game the host create a private room reachable by name.
 *
 * Message classes that menage these types of action are called Setupper.
 */
public abstract class SetUp {

    public static final String className= "SetUp";
    private final String playerName;
    private final String roomName;
    private final int numOfPlayers;


    /**
     * Default constructor.
     * @param playerName
     * @param roomName
     * @param numOfPlayers
     */
    public SetUp(String playerName, String roomName, int numOfPlayers) {
        this.playerName=playerName;
        this.roomName=roomName;
        this.numOfPlayers=numOfPlayers;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getClassName() {
        return className;
    }

    public int getNumOfPlayers(){
        return numOfPlayers;
    }

}
