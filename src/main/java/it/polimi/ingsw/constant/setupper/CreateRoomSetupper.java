package it.polimi.ingsw.constant.setupper;

public class CreateRoomSetupper extends SetUp {

    public static String className= "CreateRoomSetupper";

    public CreateRoomSetupper(String playerName, String roomName, int numOfPlayers){
        super(playerName, roomName, numOfPlayers);
    }

    @Override
    public String getClassName() {
        return className;
    }
}
