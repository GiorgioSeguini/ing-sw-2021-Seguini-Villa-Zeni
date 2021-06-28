package it.polimi.ingsw.constant.setupper;

public abstract class SetUp {

    public static final String className= "SetUp";
    private final String playerName;
    private final String roomName;
    private final int numOfPlayers;

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
