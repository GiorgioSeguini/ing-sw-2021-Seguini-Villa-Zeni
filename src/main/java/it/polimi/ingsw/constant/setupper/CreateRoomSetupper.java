package it.polimi.ingsw.constant.setupper;

public class CreateRoomSetupper extends SetUp {

    public static String className= "CreateRoomSetupper";
    private int numOfPlayers;


    public CreateRoomSetupper(String playerName, String roomName, int numOfPlayers){
        super(playerName, roomName);
        if(numOfPlayers<1 || numOfPlayers>4){
            throw new IllegalArgumentException();
        }
        else{
            this.numOfPlayers = numOfPlayers;
        }
    }

    @Override
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    public String getClassName() {
        return className;
    }
}
