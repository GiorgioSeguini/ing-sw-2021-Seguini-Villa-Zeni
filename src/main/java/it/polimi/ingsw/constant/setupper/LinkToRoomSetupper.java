package it.polimi.ingsw.constant.setupper;

/**
 * Message sent to join a private room. It also menage the reconnection.
 * @see it.polimi.ingsw.constant.setupper.SetUp
 */
public class LinkToRoomSetupper extends SetUp{
    public static final String className="LinkToRoomSetupper";

    /**
     * Default constructor.
     * Notice: setting number of playing players is an unnecessary information.
     * @param playerName
     * @param roomName
     */
    public LinkToRoomSetupper(String playerName, String roomName) {
        super(playerName, roomName, 0);
    }

    @Override
    public String getClassName() {
        return className;
    }
}
