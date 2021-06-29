package it.polimi.ingsw.constant.setupper;


/**
 * Message sent to create a room for the private game.
 * @see it.polimi.ingsw.constant.setupper.SetUp
 */
public class CreateRoomSetupper extends SetUp {

    public static final String className= "CreateRoomSetupper";


    /**
     * Default constructor.
     * @param playerName
     * @param roomName
     * @param numOfPlayers
     */
    public CreateRoomSetupper(String playerName, String roomName, int numOfPlayers){
        super(playerName, roomName, numOfPlayers);
    }

    @Override
    public String getClassName() {
        return className;
    }
}
