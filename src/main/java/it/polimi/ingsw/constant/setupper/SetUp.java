package it.polimi.ingsw.constant.setupper;

public abstract class SetUp {

    public static String className= "SetUp";
    private final String playerName;
    private final String roomName;

    public SetUp(String playerName, String roomName) {
        this.playerName=playerName;
        this.roomName=roomName;
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
        return 0;
        // TODO: overraide this method
    }

}
